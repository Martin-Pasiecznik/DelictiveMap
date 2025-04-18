let map;

async function initMap() {
    // Inicializar el mapa
    map = new google.maps.Map(document.getElementById("map"), {
        center: { lat: -34.6037, lng: -58.3816 }, // Buenos Aires
        zoom: 10,
        scrollwheel: true, // Permitir zoom con la rueda del mouse
        gestureHandling: "auto", // Permitir interacciones como zoom y paneo
    });

    // Cargar los incidentes desde el backend
    const response = await fetch("http://localhost:8080/api/incidents");
    const incidents = await response.json();

    // Agregar los incidentes como marcadores
    incidents.forEach((incident) => {
        const marker = new google.maps.Marker({
            position: { lat: incident.latitude, lng: incident.longitude },
            map: map,
            title: `${incident.type}`, // Tipo de incidente
        });

        // Crear un InfoWindow para mostrar información del incidente
        const infoWindow = new google.maps.InfoWindow({
            content: `
                <div>
                    <h3>${incident.type}</h3>
                    <p><strong>Descripción:</strong> ${incident.description}</p>
                    <p><strong>Fecha:</strong> ${new Date(incident.date).toLocaleString()}</p>
                </div>
            `,
        });

        // Mostrar el InfoWindow al hacer clic en el marcador
        marker.addListener("click", () => {
            infoWindow.open(map, marker);
        });
    });

    // Selección de ubicación en el mapa
    let selectedLocation = null;
    let tempMarker = null;

    map.addListener("click", (event) => {
        selectedLocation = {
            lat: event.latLng.lat(),
            lng: event.latLng.lng(),
        };

        // Muestra las coordenadas en el formulario
        document.getElementById("incident-location").value = `${selectedLocation.lat}, ${selectedLocation.lng}`;

        // Elimina el marcador anterior si existe
        if (tempMarker) {
            tempMarker.setMap(null);
        }

        // Agrega un marcador nuevo para la ubicación seleccionada
        tempMarker = new google.maps.Marker({
            position: selectedLocation,
            map: map,
            title: "Ubicación Seleccionada",
        });
    });
}

// Registrar un incidente (POST al backend)
document.getElementById("submit-incident").addEventListener("click", async () => {
    const date = new Date(); // Fecha actual
    const type = document.getElementById("incident-name").value;
    const description = document.getElementById("incident-description").value;
    const location = document.getElementById("incident-location").value;

    if (!location) {
        alert("Por favor, selecciona una ubicación en el mapa.");
        return;
    }

    if (!type || !description) {
        alert("Por favor, completa todos los campos.");
        return;
    }

    const [lat, lng] = location.split(",").map((coord) => parseFloat(coord.trim()));

    const incident = {
        date: date,
        description: description,
        latitude: lat,
        longitude: lng,
        type: type,
    };

    try {
        const response = await fetch("http://localhost:8080/api/incidents", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(incident),
        });

        if (response.ok) {
            alert("Incidente registrado con éxito.");
            document.getElementById("add-incident-form").reset();
        } else {
            alert("Error al guardar el incidente.");
        }
    } catch (error) {
        console.error("Error:", error);
        alert("No se pudo conectar con el servidor.");
    }
});

// Inicia el mapa al cargar la ventana
window.onload = initMap;
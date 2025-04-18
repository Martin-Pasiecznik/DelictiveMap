{
	let map;

        async function initMap() {
            // Inicializar el mapa
            map = new google.maps.Map(document.getElementById("map"), {
                center: { lat: -34.6037, lng: -58.3816 }, // Buenos Aires
                zoom: 8,
            });
				

            // Cargar los incidentes desde el backend
            const response = await fetch("http://localhost:8080/api/incidents");
            const incidentes = await response.json();

            // Agregar los incidentes como marcadores
            incidentes.forEach(incidente => {
                new google.maps.Marker({
                    position: { lat: incidente.latitude, lng: incidente.longitude },
                    map: map,
                    title: `${incidente.type}: ${incidente.descripcion}`,
                });
            });
			/////
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
				
				// Agrega un marcador nuevo
				tempMarker = new google.maps.Marker({
				    position: selectedLocation,
				    map: map,
				    title: "Ubicación Seleccionada",
				});

			  });
        }
		
	   /// POST Incident
	   document.getElementById("submit-incident").addEventListener("click", async () => {
	       const name = document.getElementById("incident-name").value;
	       const description = document.getElementById("incident-description").value;
	       const location = document.getElementById("incident-location").value;
		   
		   if(!location){
				alert("Por favor seleccione el sitio en el mapa.");
				return;
		   }

	       if (!name || !description) {
	           alert("Por favor completa todos los campos.");
	           return;
	       }

	       const [lat, lng] = location.split(",").map(coord => parseFloat(coord.trim()));

	       const incident = {
	           name: name,
	           description: description,
	           latitude: lat,
	           longitude: lng,
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

	   

        // Inicia el mapa
        window.onload = initMap;
}
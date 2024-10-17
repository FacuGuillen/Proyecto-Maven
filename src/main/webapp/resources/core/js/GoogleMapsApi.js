let userCoords;
let map;
function initMap() {
    const argCoords = { lat: -33.61, lng: -63.61 };
    map = new google.maps.Map(document.getElementById('map'), {
        center: argCoords,
        zoom: 12
    });

    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition((position) => {
            userCoords = {
                lat: position.coords.latitude,
                lng: position.coords.longitude
            };
            map.setCenter(userCoords);

            const userMarker = new google.maps.Marker({
                position: userCoords,
                map: map,
                title: 'Mi ubicación'
            });

            const infowindowUser = new google.maps.InfoWindow({
                content: '<h5>Mi ubicación actual</h5>'
            });

            userMarker.addListener('click', function() {
                infowindowUser.open(map, userMarker);
            });


        }, (error) => {
            alert("Tu navegador está bien, pero ocurrió un error al obtener la ubicación: " + error.message);
        });
    } else {
        alert("No se pudo obtener la ubicación del usuario.");
    }
}


// Función para mostrar la ruta entre dos puntos
function mostrarRuta(origin, destination, map) {
    const directionsService = new google.maps.DirectionsService();
    const directionsRenderer = new google.maps.DirectionsRenderer();

    directionsRenderer.setMap(map);

    const request = {
        origin: origin,
        destination: destination,
        travelMode: google.maps.TravelMode.DRIVING
    };

    directionsService.route(request, (result, status) => {
        if (status == google.maps.DirectionsStatus.OK) {
            directionsRenderer.setDirections(result);
        } else {
            alert('No se pudieron obtener direcciones: ' + status);
        }
    });
}
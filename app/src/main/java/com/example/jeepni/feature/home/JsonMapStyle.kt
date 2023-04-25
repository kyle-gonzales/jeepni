package com.example.jeepni.feature.home

/* edit with https://mapstyle.withgoogle.com/ */

object JsonMapStyle {
    val LIGHT_MAP_STYLE =
    """
        [
          {
            "featureType": "administrative",
            "stylers": [
              {
                "visibility": "off"
              }
            ]
          },
          {
            "featureType": "administrative",
            "elementType": "labels.text.stroke",
            "stylers": [
              {
                "weight": 2
              }
            ]
          },
          {
            "featureType": "administrative.locality",
            "elementType": "labels",
            "stylers": [
              {
                "visibility": "on"
              }
            ]
          },
          {
            "featureType": "administrative.neighborhood",
            "elementType": "labels",
            "stylers": [
              {
                "visibility": "on"
              }
            ]
          },
          {
            "featureType": "landscape",
            "stylers": [
              {
                "visibility": "off"
              }
            ]
          },
          {
            "featureType": "landscape.natural",
            "stylers": [
              {
                "color": "#ffffff"
              },
              {
                "visibility": "on"
              }
            ]
          },
          {
            "featureType": "poi",
            "stylers": [
              {
                "visibility": "off"
              }
            ]
          },
          {
            "featureType": "poi",
            "elementType": "geometry",
            "stylers": [
              {
                "visibility": "off"
              }
            ]
          },
          {
            "featureType": "poi",
            "elementType": "labels.icon",
            "stylers": [
              {
                "color": "#002202"
              }
            ]
          },
          {
            "featureType": "poi",
            "elementType": "labels.text.fill",
            "stylers": [
              {
                "color": "#002202"
              }
            ]
          },
          {
            "featureType": "road",
            "elementType": "geometry.fill",
            "stylers": [
              {
                "color": "#ffffff"
              },
              {
                "visibility": "on"
              }
            ]
          },
          {
            "featureType": "road",
            "elementType": "geometry.stroke",
            "stylers": [
              {
                "color": "#006e14"
              },
              {
                "visibility": "on"
              }
            ]
          },
          {
            "featureType": "road",
            "elementType": "labels.text.fill",
            "stylers": [
              {
                "color": "#002202"
              }
            ]
          },
          {
            "featureType": "road",
            "elementType": "labels.text.stroke",
            "stylers": [
              {
                "color": "#ffffff"
              },
              {
                "weight": 2
              }
            ]
          },
          {
            "featureType": "road.arterial",
            "elementType": "geometry.fill",
            "stylers": [
              {
                "weight": 8
              }
            ]
          },
          {
            "featureType": "road.arterial",
            "elementType": "geometry.stroke",
            "stylers": [
              {
                "weight": 2
              }
            ]
          },
          {
            "featureType": "road.highway",
            "elementType": "geometry.fill",
            "stylers": [
              {
                "weight": 8
              }
            ]
          },
          {
            "featureType": "road.highway",
            "elementType": "geometry.stroke",
            "stylers": [
              {
                "weight": 2
              }
            ]
          },
          {
            "featureType": "road.local",
            "elementType": "geometry.fill",
            "stylers": [
              {
                "color": "#dee4e2"
              },
              {
                "weight": 4
              }
            ]
          },
          {
            "featureType": "transit",
            "stylers": [
              {
                "visibility": "off"
              }
            ]
          },
          {
            "featureType": "water",
            "elementType": "geometry.fill",
            "stylers": [
              {
                "color": "#38656a"
              }
            ]
          },
          {
            "featureType": "water",
            "elementType": "labels",
            "stylers": [
              {
                "visibility": "off"
              }
            ]
          }
        ]
    """.trimIndent()
    val DARK_MAP_STYLE =
        """
            [
              {
                "featureType": "administrative",
                "stylers": [
                  {
                    "visibility": "off"
                  }
                ]
              },
              {
                "featureType": "administrative",
                "elementType": "geometry.fill",
                "stylers": [
                  {
                    "color": "#283129"
                  }
                ]
              },
              {
                "featureType": "administrative.locality",
                "elementType": "labels",
                "stylers": [
                  {
                    "visibility": "on"
                  }
                ]
              },
              {
                "featureType": "administrative.neighborhood",
                "elementType": "labels",
                "stylers": [
                  {
                    "visibility": "on"
                  }
                ]
              },
              {
                "featureType": "landscape",
                "stylers": [
                  {
                    "visibility": "off"
                  }
                ]
              },
              {
                "featureType": "landscape.natural",
                "elementType": "geometry.fill",
                "stylers": [
                  {
                    "color": "#283129"
                  },
                  {
                    "visibility": "on"
                  }
                ]
              },
              {
                "featureType": "poi",
                "stylers": [
                  {
                    "visibility": "off"
                  }
                ]
              },
              {
                "featureType": "road",
                "elementType": "geometry.fill",
                "stylers": [
                  {
                    "color": "#315539"
                  }
                ]
              },
              {
                "featureType": "road",
                "elementType": "geometry.stroke",
                "stylers": [
                  {
                    "color": "#151f17"
                  }
                ]
              },
              {
                "featureType": "road",
                "elementType": "labels.text.fill",
                "stylers": [
                  {
                    "color": "#ffffff"
                  }
                ]
              },
              {
                "featureType": "road",
                "elementType": "labels.text.stroke",
                "stylers": [
                  {
                    "color": "#283129"
                  },
                  {
                    "visibility": "off"
                  }
                ]
              },
              {
                "featureType": "road.arterial",
                "elementType": "geometry.fill",
                "stylers": [
                  {
                    "weight": 8
                  }
                ]
              },
              {
                "featureType": "road.arterial",
                "elementType": "geometry.stroke",
                "stylers": [
                  {
                    "weight": 2
                  }
                ]
              },
              {
                "featureType": "road.highway",
                "elementType": "geometry.fill",
                "stylers": [
                  {
                    "weight": 8
                  }
                ]
              },
              {
                "featureType": "road.highway",
                "elementType": "geometry.stroke",
                "stylers": [
                  {
                    "weight": 2
                  }
                ]
              },
              {
                "featureType": "road.local",
                "elementType": "geometry.fill",
                "stylers": [
                  {
                    "color": "#42493f"
                  },
                  {
                    "weight": 4
                  }
                ]
              },
              {
                "featureType": "road.local",
                "elementType": "labels",
                "stylers": [
                  {
                    "visibility": "off"
                  }
                ]
              },
              {
                "featureType": "transit",
                "stylers": [
                  {
                    "visibility": "off"
                  }
                ]
              },
              {
                "featureType": "water",
                "elementType": "geometry.fill",
                "stylers": [
                  {
                    "color": "#32657a"
                  }
                ]
              }
            ]
        """.trimIndent()
}
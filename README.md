# weather-forecasting-app

A weather-forecasting application that takes geographic US latitude/longitude values as input and presents the weather for a point in time at the given location(s).

This application:
- Accepts geographic US latitude/longitude values as input.
  For example: 38.2527° N, 85.7585° W
- Retrieves weather data using this free weather service: API Web Service 
  (https://www.weather.gov/documentation/services-web-api&sa=D&source=calendar&ust=1641768438693965&usg=AOvVaw3OWCV8Z2DjkIpuF6eXz_L2)
- Presents the “temperature” value for “Wednesday Night” at the input location.

This is developed using java language as a sprint boot application. 
A rest API is exposed to get the temperature forecast. The API takes latitude/longitude values as input, and returns temperature.
Example URL: http://localhost:8083/temperature/39.7456/-97.0892

Improvements needed:
- Invalid input values handling needs to be developed.
- Need to handle unsupported input range.
- Need to add support for differnet formats of input.
- Need to handle the scenarios where the dependent APIs not working.
- Need to support more wether parameters like windspeed.

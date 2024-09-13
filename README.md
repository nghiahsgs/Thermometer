# Thermometer
Thermometer

How to install java
```
brew install java
```

How to install maven
```
brew install maven
```


How to run the project
```
mvn -version
mvn compile exec:java
```

the example output should be like this
```
Current temperature: 25.0°C (32.00°F)

Current temperature: 10.0°C (77.00°F)

Current temperature: 5.0°C (50.00°F)

Current temperature: 1.0°C (41.00°F)

Current temperature: 0.2°C (33.80°F)
=>>> Alert: Temperature has reached the threshold of 0.0°C!

Current temperature: -0.3°C (32.36°F)

Current temperature: 0.0°C (31.46°F)

Current temperature: 0.5°C (32.00°F)

Current temperature: 1.0°C (32.90°F)

Current temperature: 50.0°C (33.80°F)

Current temperature: 105.0°C (122.00°F)
=>>> Alert: Temperature has reached the threshold of 100.0°C!

Current temperature: 110.0°C (221.00°F)

Current temperature: 97.0°C (230.00°F)
=>>> Alert: Temperature has reached the threshold of 100.0°C!
```

How to run unit tests of the project
```
mvn clean test
```
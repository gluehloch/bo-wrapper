

```
JAVA_OPTS=-Duser.timezone=Europe/Berlin
```

# Rest Service für Betoffice

** Meisterschaft

GET    /season
GET    /season/{seasonId}
POST   /season
PUT    /season/{seasonId}
DELETE /season/{seasonId}

** Spieltag einer Meisterschaft

GET    /season/{seasonId}/round/
GET    /season/{seasonId}/round/{roundId}
POST   /season/{seasonId}/round/{roundId}
PUT    /season/{seasonId}/round/{roundId}
DELETE /season/{seasonId}/round/{roundId}

** Spiel an einem Spieltag

GET    /season/{seasonId}/round/{roundId}/match
GET    /season/{seasonId}/round/{roundId}/match/{matchId}
POST   /season/{seasonId}/round/{roundId}/match/{matchId}
PUT    /season/{seasonId}/round/{roundId}/match/{matchId}
DELETE /season/{seasonId}/round/{roundId}/match/{matchId}

** Gruppe

GET    /season/{seasonId}/group
GET    /season/{seasonId}/group/{groupId}
POST   /season/{seasonId}/group/{groupId}
PUT    /season/{seasonId}/group/{groupId}
DELETE /season/{seasonId}/group/{groupId}

** Tipp

GET    /season/{seasonId}/round/{roundId}/tipp
POST   /season/{seasonId}/round/{roundId}/tipp
PUT    /season/{seasonId}/round/{roundId}/tipp
DELETE /season/{seasonId}/round/{roundId}/tipp


## Identifikator für Meisterschaft und Spieltag

* Name und Jahrgang vs seasonId
* Index vs roundId

# Docker & PostgreSQL – Komplett README (TXT)

Detta är en komplett textbaserad README för din miljö med Docker, PostgreSQL 17 och pgAdmin 4.
Allt är formaterat för att fungera i en .txt-fil (du kan även döpa den till README.md om du vill).

==================================================
1) ÖVERSIKT
==================================================
- PostgreSQL 17 körs i Docker (tjänstnamn: uni-postgres)
- pgAdmin 4 körs i Docker (webb på http://localhost:5050) och/eller som Desktop-app
- Init-skript i docker/init/ skapar roller, databaser, tabeller och seed-data första gången

==================================================
2) PROJEKTSTRUKTUR
==================================================

```text
D0031N-EOA-SOA-backend/
│
├── docker/
│   ├── init/                     # SQL-skript som körs vid första init
│   │   ├── 00_roles.sql
│   │   ├── 01_databases.sql
│   │   ├── 10_epok_schema.sql
│   │   ├── 11_epok_seed.sql
│   │   ├── 20_studentits_schema.sql
│   │   ├── 21_studentits_seed.sql
│   │   ├── 30_ladok_schema.sql
│   │   ├── 31_ladok_seed.sql
│   └── pgadmin/
│       └── servers.json          # Fördefinierade pgAdmin-anslutningar (webb)
│
├── docker-compose.yml
├── .env                          # Miljövariabler (lösenord, portar, mm)
└── README.txt                    # Denna fil (kan döpas till README.md)
```

==================================================
3) .ENV – MILJÖVARIABLER
==================================================
Exempelvärden (byt gärna till starkare lösenord i din egna .env):

POSTGRES_SUPERUSER=postgres
POSTGRES_PASSWORD=Linus

PGADMIN_EMAIL=linus.sideback03@gmail.com
PGADMIN_PASSWORD=Linus

# Host-port som mappas till Postgres i containern
PG_PORT=5433

OBS: Lägg .env i .gitignore. Skapa en .env.example utan hemligheter för repo.

==================================================
4) STARTA DOCKER-MILJÖN
==================================================
Kör i projektroten (C:\Users\Linus\IdeaProjects\D0031N-EOA-SOA-backend):

- Stäng gamla containrar (valfritt):
  docker compose down

- Nollställ DB-volymen (valfritt – raderar data, kör init på nytt):
  docker volume rm d0031n-eoa-soa-backend_pgdata

- Starta allt:
  docker compose up -d

- Verifiera:
  docker ps
  (Du bör se uni-postgres på 0.0.0.0:5433->5432/tcp och pgadmin på 0.0.0.0:5050->80/tcp)

==================================================
5) INIT-SKRIPT – VAD SOM SKAPAS
==================================================
Första gången volymen skapas körs alla .sql i docker/init/ i filnamnsordning och skapar bl.a.:
- Roller: epok, ladok, studentits
- Databaser: epok, ladok, studentits
- Scheman/tabeller och seed-data

Visa Postgres-loggar:
  docker compose logs postgres

Lista databaser i containern:
  docker exec -it uni-postgres psql -U postgres -d postgres -c "SELECT datname FROM pg_database WHERE datistemplate = false ORDER BY 1;"

==================================================
6) ANSLUTNINGAR
==================================================
A) pgAdmin Web (Docker)
- URL: http://localhost:5050
- Login:
  Email:    linus.sideback03@gmail.com
  Password: Linus
- Serverns host inuti pgAdmin Web ska vara: postgres (Docker-tjänstnamnet)

B) pgAdmin Desktop (installerad app)
- Register → Server → Connection:
  Host: localhost
  Port: 5433
  Maintenance DB: postgres
  Username: postgres
  Password: Linus
  SSL mode: Prefer

==================================================
7) BACKUP & RESTORE (I DOCKER)
==================================================
A) Full backup av hela klustret (roller + alla DB):
  docker exec -t uni-postgres pg_dumpall -U postgres -f /tmp/backup_all.sql
  docker cp uni-postgres:/tmp/backup_all.sql .\backup_all.sql

B) Per-databas backup (custom format – rekommenderas för snabb restore):
  docker exec -t uni-postgres pg_dump -U postgres -d epok       -Fc -f /tmp/epok.dump
  docker exec -t uni-postgres pg_dump -U postgres -d ladok      -Fc -f /tmp/ladok.dump
  docker exec -t uni-postgres pg_dump -U postgres -d studentits -Fc -f /tmp/studentits.dump
  docker cp uni-postgres:/tmp/epok.dump       .\epok.dump
  docker cp uni-postgres:/tmp/ladok.dump      .\ladok.dump
  docker cp uni-postgres:/tmp/studentits.dump .\studentits.dump

==================================================
8) FLYTTA FRÅN DOCKER → LOKAL POSTGRES (UTAN DOCKER)
==================================================
Förutsättningar:
- Lokal Postgres-tjänst körs på localhost:5432 och har ett känt lösenord (t.ex. Linus för användaren postgres)
- Stoppa Docker-Postgres om den också använder port 5432

METOD 1 – Per databas (rekommenderad):
1) Skapa tomma DB:er lokalt (i pgAdmin Desktop eller via kommando):
   createdb -h localhost -p 5432 -U postgres epok
   createdb -h localhost -p 5432 -U postgres ladok
   createdb -h localhost -p 5432 -U postgres studentits

2) Återställ .dump-filerna lokalt via tillfällig klientcontainer:
   docker run --rm -e PGPASSWORD=Linus -v ${PWD}:/backup postgres:17 \
     pg_restore -h host.docker.internal -p 5432 -U postgres -d epok       -c -j4 /backup/epok.dump

   docker run --rm -e PGPASSWORD=Linus -v ${PWD}:/backup postgres:17 \
     pg_restore -h host.docker.internal -p 5432 -U postgres -d ladok      -c -j4 /backup/ladok.dump

   docker run --rm -e PGPASSWORD=Linus -v ${PWD}:/backup postgres:17 \
     pg_restore -h host.docker.internal -p 5432 -U postgres -d studentits -c -j4 /backup/studentits.dump

3) Uppdatera collation-version (om Postgres varnar):
   ALTER DATABASE epok       REFRESH COLLATION VERSION;
   ALTER DATABASE ladok      REFRESH COLLATION VERSION;
   ALTER DATABASE studentits REFRESH COLLATION VERSION;

4) Kör sedan REINDEX på varje databas:
   REINDEX (VERBOSE) DATABASE epok;
   REINDEX (VERBOSE) DATABASE ladok;
   REINDEX (VERBOSE) DATABASE studentits;
   REINDEX (VERBOSE) DATABASE postgres;

==================================================
9) SPRING BOOT – EXEMPEL PÅ KONFIG
==================================================
application.properties:

spring.datasource.url=jdbc:postgresql://localhost:5433/postgres
spring.datasource.username=postgres
spring.datasource.password=Linus
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.hibernate.ddl-auto=none

(Om du kör helt lokalt utan Docker, byt port till 5432.)

==================================================
10) SAMMANFATTNING
==================================================
- Docker-miljön: Postgres på localhost:5433, pgAdmin på http://localhost:5050
- Desktop pgAdmin kopplar till Docker med Host=localhost, Port=5433
- För att flytta till lokal Postgres (5432): dumpa per DB i Docker och pg_restore lokalt
- Efter återställning: kör REFRESH COLLATION VERSION och REINDEX (VERBOSE) DATABASE ...
- Undvik PowerShell-redirection (skapar UTF-16-filer) – dumpa alltid inuti containern och docker cp ut

Du har nu en komplett guide för att köra, flytta och underhålla din databasmiljö i Docker och lokalt.

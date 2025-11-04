# 🐳 Docker Setup – PostgreSQL + pgAdmin

Detta projekt innehåller en färdig **Docker-baserad databas­miljö** bestående av:

- **PostgreSQL 17** – databas­server  
- **pgAdmin 4** – administrationsgränssnitt (både web och desktop)  
- Automatiska **init-skript** som skapar roller, databaser, tabeller och seed-data

Perfekt för lokal utveckling av backend och API-projekt.

---

## 🧱 Struktur

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
│       └── servers.json          # Fördefinierade pgAdmin-anslutningar
│
├── docker-compose.yml
├── .env                          # Miljövariabler (lösenord, portar, mm)
└── README.md                     # Denna fil

---

## ⚙️ Konfiguration (.env)

# Postgres  
POSTGRES_SUPERUSER=postgres  
POSTGRES_PASSWORD=Linus  

# pgAdmin  
PGADMIN_EMAIL=linus.sideback03@gmail.com  
PGADMIN_PASSWORD=Linus  

# Host port for database  
PG_PORT=5433  

---

## 🚀 Starta miljön

docker compose down  
docker volume rm d0031n-eoa-soa-backend_pgdata  
docker compose up -d  

docker ps

uni-postgres   → 0.0.0.0:5433->5432/tcp  
pgadmin        → 0.0.0.0:5050->80/tcp  

---

## 💻 Anslutning

### pgAdmin Web
http://localhost:5050  
Email: linus.sideback03@gmail.com  
Password: Linus  

### pgAdmin Desktop
Host: localhost  
Port: 5433  
User: postgres  
Password: Linus  

---

## 🧩 Backup & Restore

Backup:  
docker exec -t uni-postgres pg_dumpall -U postgres > backup.sql  

Restore:  
docker exec -i uni-postgres psql -U postgres -f /path/in/container/backup.sql  

Du har nu en **helt fungerande lokal PostgreSQL-databas i Docker** som fungerar med både pgAdmin och Spring Boot.

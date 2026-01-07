
CREATE DATABASE mini_football_db;


CREATE USER mini_football_db_manager WITH PASSWORD 'manager123';


GRANT ALL PRIVILEGES ON DATABASE mini_football_db TO mini_football_db_manager;


\c mini_football_db;

GRANT ALL ON SCHEMA public TO mini_football_db_manager;

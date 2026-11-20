INSERT INTO clientservicedb.client (id, nom, age) VALUES (1, 'Amine', 27.5)
ON DUPLICATE KEY UPDATE id = id;

INSERT INTO carservicedb.car (id, brand, model, matricule, client_id)
VALUES (1, 'Renault', 'Clio', '12345-A-26', 1)
ON DUPLICATE KEY UPDATE id = id;

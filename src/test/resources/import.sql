INSERT INTO level (level_id, name) VALUES (200, 'Rookie');
INSERT INTO level (level_id, name) VALUES (300, 'Champion');
INSERT INTO level (level_id, name) VALUES (400, 'Ultimate');
INSERT INTO level (level_id, name) VALUES (500, 'Mega');

INSERT INTO element (element_id, name) VALUES (200, 'None');
INSERT INTO element (element_id, name) VALUES (300, 'Nature');
INSERT INTO element (element_id, name) VALUES (400, 'Fire');
INSERT INTO element (element_id, name) VALUES (500, 'Darkness');
INSERT INTO element (element_id, name) VALUES (600, 'Water');
INSERT INTO element (element_id, name) VALUES (700, 'Machine');

INSERT INTO digimon_type (digimon_type_id, name) VALUES (200, 'Data');
INSERT INTO digimon_type (digimon_type_id, name) VALUES (300, 'Vaccine');
INSERT INTO digimon_type (digimon_type_id, name) VALUES (400, 'Virus');

INSERT INTO attack_type (attack_type_id, name) VALUES (200, 'Attack');
INSERT INTO attack_type (attack_type_id, name) VALUES (300, 'Counter Attack');
INSERT INTO attack_type (attack_type_id, name) VALUES (400, 'Interrupt');
INSERT INTO attack_type (attack_type_id, name) VALUES (500, 'Assist');

INSERT INTO digimon (digimon_id, name, level_id, digimon_type_id, element_id) VALUES (200, 'Agumon', 200, 300, 200);
INSERT INTO digimon (digimon_id, name, level_id, digimon_type_id, element_id) VALUES (300, 'Greymon', 300, 300, 200);
INSERT INTO digimon (digimon_id, name, level_id, digimon_type_id, element_id) VALUES (400, 'MetalGreymon', 400, 300, 700);
INSERT INTO digimon (digimon_id, name, level_id, digimon_type_id, element_id) VALUES (500, 'WarGreymon', 500, 300, 200);
INSERT INTO digimon (digimon_id, name, level_id, digimon_type_id, element_id) VALUES (600, 'Omnimon', 500, 300, 200);

INSERT INTO attack (attack_id, name, attack_type_id, mp) VALUES (200, 'Blue Blaster', 200, 4);
INSERT INTO attack (attack_id, name, attack_type_id, mp) VALUES (201, 'Pepper Breath', 200, 8);
INSERT INTO attack (attack_id, name, attack_type_id, mp) VALUES (202, 'Nova Blast', 200, 12);
INSERT INTO attack (attack_id, name, attack_type_id, mp) VALUES (203, 'Giga Blaster', 200, 18);
INSERT INTO attack (attack_id, name, attack_type_id, mp) VALUES (204, 'Terra Force', 200, 40);
INSERT INTO attack (attack_id, name, attack_type_id, mp) VALUES (300, 'Smiley Bomb', 300, 16);
INSERT INTO attack (attack_id, name, attack_type_id, mp) VALUES (400, 'MP Magic', 400, 4);
INSERT INTO attack (attack_id, name, attack_type_id, mp) VALUES (500, 'Necro Magic', 500, 0);

INSERT INTO evolution (evolution_id, base_digimon_id, evolved_digimon_id, dna_times) VALUES (1200, 200, 300, "0+");
INSERT INTO evolution (evolution_id, base_digimon_id, evolved_digimon_id, dna_times) VALUES (1300, 300, 400, "0-5");
INSERT INTO evolution (evolution_id, base_digimon_id, evolved_digimon_id, dna_times) VALUES (1400, 400, 500, "0-19");

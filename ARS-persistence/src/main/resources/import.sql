INSERT INTO Address (id, street, city, state, country, postCode) VALUES (0, 'Vajnorska', 'Bratislava', NULL, 'Slovakia', 99999 );
INSERT INTO Address (id, street, city, state, country, postCode) VALUES (1, 'Pulska 15', 'Rijeka', NULL, 'Croatia', 51000 );
INSERT INTO Offer (id, name, address_id, capacity, isAnimalFriendly, isSmokerFriendly) VALUES (0, 'Best vacation ever!', 0, 9, true, true);
INSERT INTO Offer (id, name, address_id, capacity, isAnimalFriendly, isSmokerFriendly) VALUES (1, 'Romantic apartment', 1, 2, true, true);
INSERT INTO User (id, keycloakPrincipal, name, surname, email, password, isActive) VALUES (0, 'e16f9057-19a2-4776-b93f-6553aa020a02', 'Jano', 'Gabor', 'chvost30@example.com', '123456', true);
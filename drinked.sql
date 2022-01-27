-- phpMyAdmin SQL Dump
-- version 4.9.5deb2
-- https://www.phpmyadmin.net/
--
-- Hôte : localhost:3306
-- Généré le : jeu. 27 jan. 2022 à 18:19
-- Version du serveur :  8.0.27-0ubuntu0.20.04.1
-- Version de PHP : 7.4.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `drinked`
--

-- --------------------------------------------------------

--
-- Structure de la table `Beverages`
--

CREATE TABLE `Beverages` (
  `id` int NOT NULL,
  `name` varchar(20) DEFAULT NULL,
  `description` varchar(256) DEFAULT NULL,
  `price_35` float DEFAULT NULL,
  `price_75` float DEFAULT NULL,
  `quantity_available` int DEFAULT NULL,
  `water_percentage` float DEFAULT '80'
) ;

--
-- Déchargement des données de la table `Beverages`
--

INSERT INTO `Beverages` (`id`, `name`, `description`, `price_35`, `price_75`, `quantity_available`, `water_percentage`) VALUES
(1, 'Espresso', 'Short', 0.8, 1.2, 50, 80),
(2, 'Cappuccino', 'Long', 1.2, 2, 70, 80),
(3, 'Macchiato', 'Classic', 1.5, 2.5, 65, 80),
(4, 'Latte Macchiato', 'Latte', 2, 3, 100, 80);

-- --------------------------------------------------------

--
-- Structure de la table `Orders`
--

CREATE TABLE `Orders` (
  `id` int NOT NULL,
  `beverage_quantity` int DEFAULT NULL,
  `beverage_id` int DEFAULT NULL,
  `sugar_quantity` int DEFAULT NULL,
  `cup_selection` varchar(128) DEFAULT NULL,
  `price` float DEFAULT NULL,
  `validity` varchar(32) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `Orders`
--

INSERT INTO `Orders` (`id`, `beverage_quantity`, `beverage_id`, `sugar_quantity`, `cup_selection`, `price`, `validity`) VALUES
(1, 35, 2, 5, 'Personal Cup', 1.2, 'Validated'),
(2, 75, 4, 10, 'Cup 75cl', 2.85, 'Validated'),
(3, 35, 2, 5, 'Personal Cup', 1.2, 'Canceled'),
(4, 75, 2, 0, 'Personal Cup', 2, 'Validated');

-- --------------------------------------------------------

--
-- Structure de la table `Resources`
--

CREATE TABLE `Resources` (
  `id` int NOT NULL,
  `description` varchar(100) DEFAULT NULL,
  `quantity_available` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `Resources`
--

INSERT INTO `Resources` (`id`, `description`, `quantity_available`) VALUES
(1, 'Water', 150),
(2, 'Sugar', 150),
(3, 'Cup 35cl', 10),
(4, 'Cup 75cl', 15);

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `Beverages`
--
ALTER TABLE `Beverages`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `Orders`
--
ALTER TABLE `Orders`
  ADD PRIMARY KEY (`id`),
  ADD KEY `beverage_id` (`beverage_id`);

--
-- Index pour la table `Resources`
--
ALTER TABLE `Resources`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `Beverages`
--
ALTER TABLE `Beverages`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `Orders`
--
ALTER TABLE `Orders`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=29;

--
-- AUTO_INCREMENT pour la table `Resources`
--
ALTER TABLE `Resources`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `Orders`
--
ALTER TABLE `Orders`
  ADD CONSTRAINT `beverage_id` FOREIGN KEY (`beverage_id`) REFERENCES `Beverages` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

USE `dgss1819_teamB_microstories`;
--
-- Remove data for table `User`
--
DELETE FROM `User`;
--
-- Reset AUTO_INCREMENT for table `User`
--
ALTER TABLE `User` AUTO_INCREMENT = 1;
--
-- Remove data for table `Story`
--
DELETE FROM `Story`;
--
-- Reset AUTO_INCREMENT for table `Favourite`
--
ALTER TABLE `Story` AUTO_INCREMENT = 1;
--
-- Remove data for table `Story`
--
DELETE FROM `Favourite`;
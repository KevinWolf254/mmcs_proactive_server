SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;


CREATE TABLE `administrator` (
  `id` int(11) NOT NULL,
  `country_code` tinyint(4) NOT NULL,
  `phone_no` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `aeon_units` (
  `id` int(11) NOT NULL,
  `units` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `charges` (
  `id` int(11) NOT NULL,
  `country` tinyint(4) NOT NULL,
  `rwf` double NOT NULL,
  `rwf_airtel` double NOT NULL,
  `kes` double NOT NULL,
  `kes_airtel` double NOT NULL,
  `tzs` double NOT NULL,
  `tzs_airtel` double NOT NULL,
  `ugx` double NOT NULL,
  `ugx_airtel` double NOT NULL,
  `other` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `exchange_rates` (
  `id` int(11) NOT NULL,
  `country` tinyint(4) NOT NULL,
  `rwf` double NOT NULL,
  `kes` double NOT NULL,
  `tzs` double NOT NULL,
  `ugx` double NOT NULL,
  `dollar` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `organisation` (
  `id` int(11) NOT NULL,
  `country` tinyint(4) NOT NULL,
  `name` varchar(255) NOT NULL,
  `enabled` tinyint(4) NOT NULL,
  `created_on` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `org_administrators` (
  `org_fk` int(11) NOT NULL,
  `admin_fk` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `org_payments` (
  `org_fk` int(11) NOT NULL,
  `payment_fk` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `org_trans_costs` (
  `org_fk` int(11) NOT NULL,
  `trans_cost_fk` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `org_units` (
  `id` int(11) NOT NULL,
  `units_available` double NOT NULL,
  `org_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `org_units_requests` (
  `org_fk` int(11) NOT NULL,
  `request_fk` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `payment` (
  `id` int(11) NOT NULL,
  `transaction_id` varchar(255) NOT NULL,
  `category` varchar(255) NOT NULL,
  `source` varchar(255) NOT NULL,
  `provider` varchar(255) NOT NULL,
  `provider_ref_id` varchar(255) NOT NULL,
  `currency` tinyint(1) NOT NULL,
  `amount` double NOT NULL,
  `transaction_fee` double NOT NULL,
  `provider_fee` double NOT NULL,
  `date` datetime NOT NULL,
  `status` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `sender_identifier` (
  `id` int(11) NOT NULL,
  `name` varchar(11) NOT NULL,
  `paid` tinyint(4) NOT NULL,
  `org_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `transaction_cost` (
  `id` int(11) NOT NULL,
  `amount` double NOT NULL,
  `message_id` varchar(255) NOT NULL,
  `date` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `units_request` (
  `id` int(11) NOT NULL,
  `amount_requested` double NOT NULL,
  `mpesa_trans_no` varchar(11) NOT NULL,
  `request_date` datetime NOT NULL,
  `request_status` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `verification_token` (
  `id` int(11) NOT NULL,
  `token` varchar(255) NOT NULL,
  `expiry_date` date NOT NULL,
  `org_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


ALTER TABLE `administrator`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `aeon_units`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `charges`
  ADD UNIQUE KEY `country` (`country`),
  ADD KEY `id` (`id`);

ALTER TABLE `exchange_rates`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `currency` (`country`);

ALTER TABLE `organisation`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `org_administrators`
  ADD PRIMARY KEY (`org_fk`,`admin_fk`);

ALTER TABLE `org_payments`
  ADD PRIMARY KEY (`org_fk`,`payment_fk`);

ALTER TABLE `org_trans_costs`
  ADD PRIMARY KEY (`org_fk`,`trans_cost_fk`);

ALTER TABLE `org_units`
  ADD PRIMARY KEY (`id`),
  ADD KEY `org_id` (`org_id`);

ALTER TABLE `org_units_requests`
  ADD PRIMARY KEY (`org_fk`,`request_fk`);

ALTER TABLE `payment`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `transaction_id` (`transaction_id`),
  ADD UNIQUE KEY `provider_ref_id` (`provider_ref_id`);

ALTER TABLE `sender_identifier`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `name` (`name`);

ALTER TABLE `transaction_cost`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `message_id` (`message_id`);

ALTER TABLE `units_request`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `mpesa_trans_no` (`mpesa_trans_no`);

ALTER TABLE `verification_token`
  ADD PRIMARY KEY (`id`);


ALTER TABLE `administrator`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=212;
ALTER TABLE `aeon_units`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
ALTER TABLE `charges`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
ALTER TABLE `exchange_rates`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
ALTER TABLE `organisation`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=108;
ALTER TABLE `org_units`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=408;
ALTER TABLE `payment`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=46;
ALTER TABLE `sender_identifier`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=308;
ALTER TABLE `transaction_cost`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
ALTER TABLE `units_request`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=36;
ALTER TABLE `verification_token`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

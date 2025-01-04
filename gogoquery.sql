-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 17, 2024 at 05:31 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `gogoquery`
--

-- --------------------------------------------------------

--
-- Table structure for table `mscart`
--

CREATE TABLE `mscart` (
  `UserID` int(11) DEFAULT NULL,
  `ItemID` int(11) DEFAULT NULL,
  `Quantity` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `mscart`
--

INSERT INTO `mscart` (`UserID`, `ItemID`, `Quantity`) VALUES
(24, 47, 1),
(24, 46, 1),
(24, 31, 1),
(16, 66, 1),
(16, 36, 1),
(16, 56, 1);

-- --------------------------------------------------------

--
-- Table structure for table `msitem`
--

CREATE TABLE `msitem` (
  `ItemID` int(11) NOT NULL,
  `ItemName` varchar(255) DEFAULT NULL,
  `ItemPrice` double DEFAULT NULL,
  `ItemDesc` varchar(255) DEFAULT NULL,
  `ItemStock` int(11) DEFAULT NULL,
  `ItemCategory` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `msitem`
--

INSERT INTO `msitem` (`ItemID`, `ItemName`, `ItemPrice`, `ItemDesc`, `ItemStock`, `ItemCategory`) VALUES
(1, 'Sennheiser Game ONE Gaming Headset', 149.99, 'Open Acoustic Gaming Headset with Natural Sound', 30, 'Headset'),
(2, 'Corsair Virtuoso RGB Wireless SE Gaming Headset', 209.99, 'High-Fidelity Wireless Gaming Headset with RGB Lighting', 25, 'Headset'),
(3, 'Logitech MX Master 3 Advanced Wireless Mouse', 99.99, 'Precision Wireless Mouse with Advanced Features', 30, 'Mouse'),
(4, 'Razer Huntsman Elite Mechanical Gaming Keyboard', 199.99, 'Opto-Mechanical Keyboard with Chroma RGB', 20, 'Keyboard'),
(5, 'Corsair H100i RGB Platinum AIO Cooler', 159.99, '240mm Liquid Cooler with RGB Lighting', 22, 'Cooling'),
(6, 'be quiet! Dark Rock Pro 4 CPU Cooler', 89.99, 'High-Performance CPU Cooler with Silent Operation', 25, 'Cooling'),
(7, 'Lian Li PC-O11 Dynamic ATX Mid Tower Case', 139.99, 'Sleek and Spacious Case with Dual Chamber Design', 20, 'Case'),
(8, 'Phanteks Eclipse P400A ATX Mid Tower Case', 89.99, 'High-Airflow Case with Tempered Glass Side Panel', 28, 'Case'),
(9, 'Seasonic Focus GX-750 750W 80 Plus Gold Power Supply', 109.99, 'Fully Modular Power Supply with Silent Operation', 30, 'Power Supply'),
(10, 'Corsair CX650M 650W 80 Plus Bronze Power Supply', 69.99, 'Semi-Modular Power Supply with High Efficiency', 40, 'Power Supply'),
(11, 'Gigabyte B450 AORUS PRO Wi-Fi ATX Motherboard', 109.99, 'Affordable Motherboard with Wi-Fi and RGB Lighting', 22, 'Motherboard'),
(12, 'ASUS TUF Gaming B550-PLUS ATX Motherboard', 159.99, 'Durable Motherboard with PCIe 4.0 Support', 18, 'Motherboard'),
(13, 'Seagate Barracuda 4TB HDD', 99.99, 'Reliable Hard Drive for Mass Storage', 50, 'Storage'),
(14, 'Samsung 980 PRO 1TB NVMe SSD', 199.99, 'High-Speed NVMe SSD with Up to 7000 MB/s Read Speed', 20, 'Storage'),
(15, 'G.SKILL Ripjaws V 32GB (2 x 16GB) DDR4-3600', 149.99, 'High-Performance RAM with Low Latency', 30, 'Memory'),
(16, 'Corsair Vengeance RGB Pro 16GB (2 x 8GB) DDR4-3200', 94.99, 'High-Performance RGB RAM for Gaming', 45, 'Memory'),
(17, 'AMD Radeon RX 6700 XT', 479.99, 'Powerful Graphics Card with 12GB GDDR6 Memory', 14, 'Graphics Card'),
(18, 'NVIDIA GeForce RTX 3070', 499.99, 'High-end Graphics Card with 8GB GDDR6 Memory', 12, 'Graphics Card'),
(19, 'AMD Ryzen 5 5600X', 299, '6-Core Processor with a 3.7 GHz Base Clock', 30, 'Processor'),
(20, 'Intel Core i5-11600K', 262.99, '6-Core Processor with Turbo Boost up to 4.9 GHz', 25, 'Processor'),
(21, 'HyperX Cloud II Gaming Headset', 99.99, 'Comfortable Gaming Headset with 7.1 Surround Sound', 40, 'Headset'),
(22, 'SteelSeries Arctis Pro Wireless Gaming Headset', 329.99, 'High-Fidelity Wireless Gaming Headset with Dual Wireless', 18, 'Headset'),
(23, 'Logitech G502 HERO High Performance Gaming Mouse', 49.99, 'Gaming Mouse with HERO 25K Sensor and RGB Lighting', 35, 'Mouse'),
(24, 'Razer BlackWidow Elite Mechanical Gaming Keyboard', 169.99, 'Mechanical Keyboard with Razer Green Switches', 25, 'Keyboard'),
(25, 'Noctua NH-D15 Premium CPU Cooler', 89.99, 'Dual Tower CPU Cooler with Excellent Cooling Performance', 20, 'Cooling'),
(26, 'Cooler Master Hyper 212 RGB Black Edition', 49.99, 'RGB Air Cooler with Four Heatpipes for Efficient Cooling', 45, 'Cooling'),
(27, 'Fractal Design Meshify C ATX Mid Tower Case', 109.99, 'High-Airflow Case with Tempered Glass Side Panel', 30, 'Case'),
(28, 'NZXT H510 Elite ATX Mid Tower Case', 149.99, 'Sleek and Stylish Case with Tempered Glass Panels', 22, 'Case'),
(29, 'EVGA SuperNOVA 750 G5 750W 80 Plus Gold Power Supply', 119.99, 'Fully Modular Power Supply with Eco Mode', 28, 'Power Supply'),
(30, 'Corsair RM850x 850W 80 Plus Gold Power Supply', 129.99, 'Fully Modular Power Supply with High Efficiency', 35, 'Power Supply'),
(31, 'MSI MPG Z490 Gaming Edge WiFi ATX Motherboard', 189.99, 'Feature-Rich Motherboard with Wi-Fi 6 and M.2 Slots', 18, 'Motherboard'),
(32, 'ASUS ROG Strix X570-E Gaming ATX Motherboard', 299.99, 'High-End Motherboard with Wi-Fi 6 and PCIe 4.0', 12, 'Motherboard'),
(33, 'Western Digital Black 2TB Performance HDD', 99.99, 'High-Performance Hard Drive for Gaming and Storage', 40, 'Storage'),
(34, 'Samsung 970 EVO Plus 1TB NVMe SSD', 149.99, 'High-Speed NVMe SSD with Up to 3500 MB/s Read Speed', 25, 'Storage'),
(35, 'G.SKILL Trident Z RGB 32GB (2 x 16GB) DDR4-3600', 179.99, 'High-Performance RGB RAM with Low Latency', 30, 'Memory'),
(36, 'Corsair Vengeance LPX 16GB (2 x 8GB) DDR4-3200', 79.99, 'High-Performance RAM for Gaming and Overclocking', 50, 'Memory'),
(37, 'AMD Radeon RX 6800 XT', 649.99, 'Powerful Graphics Card with 16GB GDDR6 Memory', 8, 'Graphics Card'),
(38, 'NVIDIA GeForce RTX 3080', 699.99, 'High-end Graphics Card with 10GB GDDR6X Memory', 10, 'Graphics Card'),
(39, 'AMD Ryzen 9 5900X', 499, '12-Core Processor with a 3.7 GHz Base Clock', 15, 'Processor'),
(40, 'Intel Core i9-11900K', 539.99, '8-Core Processor with Turbo Boost up to 5.3 GHz', 20, 'Processor'),
(41, 'Electro-Harmonix Holy Grail Reverb', 127.8, 'Compact reverb pedal with spring, hall, and flerb reverb modes', 20, 'Stompbox'),
(42, 'MXR Carbon Copy Analog Delay', 149.99, 'Analog delay pedal with rich, warm delay sounds', 25, 'Stompbox'),
(43, 'Message from LC117 (Case Maker)', 479, 'Hai ges gw cm mo blg aja sbnrny logic aplikasinya itu simple bgt, dan UI itu g msk dlm scoring criteria. Jd klian jgn trlalu pusing ngrjain projectnya yaa, kalo bingung cb tanya aslabnya atau ngga tanya case makernya (gw). Goodluck!', 15, 'Easter egg'),
(44, 'TC Electronic Hall of Fame Reverb', 149, 'Versatile reverb pedal with multiple reverb types', 20, 'Stompbox'),
(45, 'Electro-Harmonix Big Muff Pi in Black', 99.99, 'Iconic fuzz pedal known for its rich, creamy distortion', 20, 'Stompbox'),
(46, 'Ibanez TS808 Tube Screamer', 179.99, 'Reissue of the classic overdrive pedal with warm, creamy tones', 30, 'Stompbox'),
(47, 'Boss RC-30 Loop Station', 299.99, 'Dual-track looper with built-in effects and memory', 25, 'Stompbox'),
(48, 'Mesa Boogie Mark V', 2599, 'High-end amplifier with a wide range of tones and features', 10, 'Amplifier'),
(49, 'Orange TH30', 999, 'Versatile amplifier with a range of tones from clean to high gain', 12, 'Amplifier'),
(50, 'VOX AC15C1', 799.99, 'Compact version of the AC30 with the same chimey tone', 15, 'Amplifier'),
(51, 'Marshall JCM800 2203', 2499.99, 'Classic Marshall amp with iconic rock tones', 8, 'Amplifier'),
(52, 'Fender Mustang GTX100', 499.99, 'Modeling amplifier with a wide range of tones and effects', 18, 'Amplifier'),
(53, 'Martin 000-15M in Dark Mahogany', 1299, 'All-mahogany acoustic guitar with warm, rich tone', 10, 'Guitar'),
(54, 'Taylor GS Mini in Natural', 599, 'Compact acoustic guitar with big sound and great playability', 25, 'Guitar'),
(55, 'Ibanez JEM77P Steve Vai Signature in White', 1599.99, 'Signature model with unique design and high-output pickups', 10, 'Guitar'),
(56, 'PRS SE Custom 24 in Tobacco Sunburst', 899, 'Affordable version of the PRS Custom 24 with versatile features', 20, 'Guitar'),
(57, 'Gibson ES-335 in Vintage Sunburst', 2999, 'Semi-hollow body guitar with a warm, resonant tone', 8, 'Guitar'),
(58, 'Gibson Les Paul Studio in Wine Red', 1499, 'Stripped-down Les Paul with powerful pickups and lightweight design', 12, 'Guitar'),
(59, 'Fender Player Telecaster in Butterscotch Blonde', 849.99, 'Affordable Telecaster with classic features and versatile tones', 18, 'Guitar'),
(60, 'Fender American Ultra Stratocaster in Arctic Pearl', 2099.99, 'High-performance electric guitar with advanced features', 20, 'Guitar'),
(61, 'Sigma Rizzler Skibideez Nuts (Ohio Size)', 1.24, 'Crunchy and flavorful nut snack only for true sigma, perfect for satisfying your cravings. Enjoy the big, bold taste of Skibideez Nuts! (Best consumed when mewing)', 69, 'Food'),
(62, 'Tokai AST-52SH VWH/CJ Goldstar Sound In Vintage White', 496.69, 'Specification :\r\n\r\nAlder body\r\nOne piece canadian maple neck with U shape and Carbonized Jatoba FB\r\n12 radius with 22 medium jumbo Sanko Japan Frets\r\nTokai STC-F Mark II x 2 and LSC-F Zebra humbuckers Mark II x 1 pickups\r\ngenuine cow bone nut\r\nTokai ST-VK', 8, 'Guitar'),
(63, 'Suhr Classic S HSS in Olympic White With Indian Rosewood Fingerboard', 3299, 'Specifications : \r\n\r\nBody Shape: Classic\r\nBody Wood: Alder\r\nPickguard: Parchment 3-Ply\r\nNeck Wood: Tinted Maple\r\nFingerboard Wood: Indian Rosewood \r\nNeck Back Shape: 60\'s C Vintage Standard .810 - .930\r\nFingerboard Radius: 9\"-12\" Compound\r\nFrets: Medium S', 1, 'Guitar'),
(65, 'Cory Wong - Power Station Exclusive Limited Edition Vinyl 2 LP', 99.98, 'Label:       Cory Wong Self-released)\r\nFormat:      2 x Vinyl, LP, Album\r\nCountry:     US\r\nReleased:    Oct 2022\r\nGenre:       Jazz, Funk / Soul\r\nStyle:       Funk, Jazz-Funk, Rhythm & Blues, Soul', 3, 'Vinyl'),
(66, 'BinusMaya 3rd Semester Premium Battlepass', 20.6, 'Premium Battlepass Rewards :\r\n- Instant A+ or even S on all subjects\r\n- Acknowledgement by all lab assistants\r\n\r\n*Valid Until : Yesterday\r\n\r\n*Side effects may include getting dropped out', 34, 'Coupon'),
(70, 'Keyboard keren', 100, 'keren', 1, NULL),
(71, 'speaker bluetooth java', 30, 'Description:', NULL, NULL),
(72, 'speaker bluetooth java', 33.13, 'Description:', NULL, NULL),
(73, 'keyboard keren', 22.12, 'Description:', 8, 'electronics'),
(74, 'mouse kece', 9.22, 'mouse kece 1.2', 18, 'electronics');

-- --------------------------------------------------------

--
-- Table structure for table `msuser`
--

CREATE TABLE `msuser` (
  `UserID` int(11) NOT NULL,
  `UserDOB` date DEFAULT NULL,
  `UserEmail` varchar(255) DEFAULT NULL,
  `UserPassword` varchar(255) DEFAULT NULL,
  `UserGender` varchar(10) DEFAULT NULL,
  `UserRole` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `msuser`
--

INSERT INTO `msuser` (`UserID`, `UserDOB`, `UserEmail`, `UserPassword`, `UserGender`, `UserRole`) VALUES
(1, '1992-08-10', 'alice@gomail.com', 'securepass', 'Female', 'Shopper'),
(2, '1987-04-25', 'bob@gomail.com', 'mysecretpassword', 'Male', 'Shopper'),
(3, '1995-12-03', 'charlie@gomail.com', 'password123', 'Male', 'Shopper'),
(4, '1990-06-18', 'diana@gomail.com', 'dianapass', 'Female', 'Shopper'),
(5, '1983-11-07', 'eric@gomail.com', 'secret123', 'Male', 'Shopper'),
(8, '1999-06-17', 'dummy@gomail.com', 'asd123', 'Male', 'Shopper'),
(9, '1999-08-12', 'newdummy@gomail.com', 'asd123', 'Male', 'Shopper'),
(16, '2004-01-31', 'elgankenlie@gomail.com', 'asd123', 'Male', 'Shopper'),
(18, '1997-06-17', 'victor@gomail.com', 'asd123', 'Male', 'Shopper'),
(19, '1998-06-23', 'helohelo@gomail.com', 'asd123', 'Female', 'Manager'),
(20, '2005-06-14', 'jekigates@gomail.com', 'jeki123', 'Male', 'Shopper'),
(21, '1942-06-18', 'bintanghalim@gomail.com', 'asd123', 'Male', 'Shopper'),
(22, '1820-05-21', 'yanto@gomail.com', 'iamyanto123', 'Male', 'Shopper'),
(23, '1820-05-21', 'bintang@gomail.com', 'ilovekwetiaumedan123', 'Male', 'Shopper'),
(24, '1820-05-21', 'lc117@gomail.com', 'elgankenlie123', 'Male', 'Shopper'),
(25, '2004-05-21', 'theojustin@gomail.com', 'TH24-1', 'Male', 'Shopper'),
(26, '2003-03-15', 'yg.di.atas.gw.pgn.pny.cwe@gomail.com', 'asd123', 'Female', 'Shopper'),
(27, '1820-05-21', 'semangat.ya.ngerjainnya@gomail.com', 'asd123', 'Female', 'Shopper'),
(28, '2004-01-21', 'nicolependek120cm@gomail.com', 'yahakupendek123', 'Female', 'Shopper'),
(29, '1989-06-09', 'duckcing@gomail.com', 'sorider123', 'Female', 'Shopper'),
(30, '2002-06-12', 'noreply.elevensevenenterprise@gomail.com', 'lc117', 'Male', 'Shopper'),
(31, '2000-01-01', 'admin@gomail.com', 'admin123', 'Male', 'Manager'),
(32, '2005-12-02', 'vincent@gomail.com', '1234', 'Male', 'Shopper'),
(33, '2000-11-28', 'shopperdude123@gomail.com', '1234', 'Male', 'Shopper'),
(34, '2005-12-02', 'manag@gomail.com', '1234', 'Male', 'Manager');

-- --------------------------------------------------------

--
-- Table structure for table `transactiondetail`
--

CREATE TABLE `transactiondetail` (
  `TransactionID` int(11) DEFAULT NULL,
  `ItemID` int(11) DEFAULT NULL,
  `Quantity` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `transactiondetail`
--

INSERT INTO `transactiondetail` (`TransactionID`, `ItemID`, `Quantity`) VALUES
(14, 66, 1),
(14, 65, 1),
(14, 63, 1),
(15, 66, 2),
(16, 61, 11),
(16, 60, 1),
(17, 58, 1),
(17, 65, 1),
(18, 56, 1),
(18, 55, 1),
(18, 66, 1),
(18, 61, 5),
(18, 54, 1),
(19, 60, 1),
(19, 57, 3),
(19, 36, 1),
(19, 28, 1),
(20, 10, 1),
(20, 21, 1),
(20, 20, 4),
(20, 11, 1),
(21, 66, 2),
(21, 1, 1),
(21, 12, 1),
(21, 24, 1),
(22, 10, 1),
(23, 41, 1),
(23, 37, 3),
(23, 27, 6),
(23, 62, 4),
(23, 51, 3),
(23, 18, 1),
(24, 1, 1),
(25, 13, 1),
(25, 11, 1),
(25, 3, 1);

-- --------------------------------------------------------

--
-- Table structure for table `transactionheader`
--

CREATE TABLE `transactionheader` (
  `TransactionID` int(11) NOT NULL,
  `UserID` int(11) DEFAULT NULL,
  `DateCreated` date DEFAULT NULL,
  `Status` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `transactionheader`
--

INSERT INTO `transactionheader` (`TransactionID`, `UserID`, `DateCreated`, `Status`) VALUES
(14, 16, '2024-06-27', 'Sent'),
(15, 28, '2024-06-27', 'In Queue'),
(16, 24, '2024-06-27', 'In Queue'),
(17, 23, '2024-06-27', 'In Queue'),
(18, 22, '2024-06-27', 'In Queue'),
(19, 20, '2024-06-27', 'In Queue'),
(20, 26, '2024-06-27', 'In Queue'),
(21, 25, '2024-06-27', 'In Queue'),
(22, 29, '2024-06-27', 'In Queue'),
(23, 30, '2024-06-27', 'In Queue'),
(24, 32, '2024-12-17', 'In Queue'),
(25, 32, '2024-12-17', 'In Queue');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `mscart`
--
ALTER TABLE `mscart`
  ADD KEY `CustomerID` (`UserID`),
  ADD KEY `ItemID` (`ItemID`);

--
-- Indexes for table `msitem`
--
ALTER TABLE `msitem`
  ADD PRIMARY KEY (`ItemID`);

--
-- Indexes for table `msuser`
--
ALTER TABLE `msuser`
  ADD PRIMARY KEY (`UserID`);

--
-- Indexes for table `transactiondetail`
--
ALTER TABLE `transactiondetail`
  ADD KEY `TransactionID` (`TransactionID`),
  ADD KEY `ItemID` (`ItemID`);

--
-- Indexes for table `transactionheader`
--
ALTER TABLE `transactionheader`
  ADD PRIMARY KEY (`TransactionID`),
  ADD KEY `CustomerID` (`UserID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `msitem`
--
ALTER TABLE `msitem`
  MODIFY `ItemID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=75;

--
-- AUTO_INCREMENT for table `msuser`
--
ALTER TABLE `msuser`
  MODIFY `UserID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=35;

--
-- AUTO_INCREMENT for table `transactionheader`
--
ALTER TABLE `transactionheader`
  MODIFY `TransactionID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `mscart`
--
ALTER TABLE `mscart`
  ADD CONSTRAINT `fk_user` FOREIGN KEY (`UserID`) REFERENCES `msuser` (`UserID`),
  ADD CONSTRAINT `mscart_ibfk_1` FOREIGN KEY (`UserID`) REFERENCES `msuser` (`UserID`) ON DELETE CASCADE,
  ADD CONSTRAINT `mscart_ibfk_2` FOREIGN KEY (`ItemID`) REFERENCES `msitem` (`ItemID`) ON DELETE CASCADE;

--
-- Constraints for table `transactiondetail`
--
ALTER TABLE `transactiondetail`
  ADD CONSTRAINT `transactiondetail_ibfk_1` FOREIGN KEY (`TransactionID`) REFERENCES `transactionheader` (`TransactionID`) ON DELETE CASCADE,
  ADD CONSTRAINT `transactiondetail_ibfk_2` FOREIGN KEY (`ItemID`) REFERENCES `msitem` (`ItemID`) ON DELETE CASCADE;

--
-- Constraints for table `transactionheader`
--
ALTER TABLE `transactionheader`
  ADD CONSTRAINT `transactionheader_ibfk_1` FOREIGN KEY (`UserID`) REFERENCES `msuser` (`UserID`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

-- Membuat database jika belum ada, dan memilihnya untuk digunakan
CREATE DATABASE IF NOT EXISTS db_penjualan_toko;
USE db_penjualan_toko;

-- Struktur dan data untuk tbl_level
CREATE TABLE `tbl_level` ( `id_level` int(11) NOT NULL, `level` varchar(50) NOT NULL ) ENGINE=InnoDB;
INSERT INTO `tbl_level` (`id_level`, `level`) VALUES (1, 'Admin'), (2, 'Kasir'), (3, 'Gudang');
ALTER TABLE `tbl_level` ADD PRIMARY KEY (`id_level`);

-- Struktur dan data untuk tbl_user
CREATE TABLE `tbl_user` ( `id_user` varchar(10) NOT NULL, `id_level` int(11) NOT NULL, `nama_user` varchar(100) NOT NULL, `jk` char(1) NOT NULL, `nope` varchar(15) NOT NULL, `username` varchar(50) NOT NULL, `password` varchar(255) NOT NULL ) ENGINE=InnoDB;
INSERT INTO `tbl_user` (`id_user`, `id_level`, `nama_user`, `jk`, `nope`, `username`, `password`) VALUES ('USER001', 1, 'Administrator', 'L', '081234567890', 'admin', 'admin');
ALTER TABLE `tbl_user` ADD PRIMARY KEY (`id_user`), ADD KEY `fk_user_level` (`id_level`);

-- Struktur dan data untuk tbl_supplier
CREATE TABLE `tbl_supplier` ( `kd_supplier` varchar(10) NOT NULL, `nama_supplier` varchar(100) NOT NULL, `nope_supplier` varchar(15) NOT NULL, `alamat` text NOT NULL ) ENGINE=InnoDB;
INSERT INTO `tbl_supplier` (`kd_supplier`, `nama_supplier`, `nope_supplier`, `alamat`) VALUES ('SUP001', 'Agus', '084632751623', 'Cigugur');
ALTER TABLE `tbl_supplier` ADD PRIMARY KEY (`kd_supplier`);

-- Struktur dan data untuk tbl_barang
CREATE TABLE `tbl_barang` ( `kd_barang` varchar(10) NOT NULL, `nama_barang` varchar(100) NOT NULL, `harga_jual` int(11) NOT NULL, `harga_beli` int(11) NOT NULL, `stok` int(11) NOT NULL ) ENGINE=InnoDB;
INSERT INTO `tbl_barang` (`kd_barang`, `nama_barang`, `harga_jual`, `harga_beli`, `stok`) VALUES ('BRG001', 'Susu', 18000, 12000, 10);
ALTER TABLE `tbl_barang` ADD PRIMARY KEY (`kd_barang`);

-- Struktur dan data untuk tbl_pembelian
CREATE TABLE `tbl_pembelian` ( `kd_pembelian` varchar(20) NOT NULL, `kd_barang` varchar(10) NOT NULL, `id_user` varchar(10) NOT NULL, `tgl_pembelian` date NOT NULL, `jmlh_beli` int(11) NOT NULL, `total_beli` int(11) NOT NULL ) ENGINE=InnoDB;
INSERT INTO `tbl_pembelian` (`kd_pembelian`, `kd_barang`, `id_user`, `tgl_pembelian`, `jmlh_beli`, `total_beli`) VALUES ('PB1750154239053', 'BRG001', 'USER001', '2025-06-17', 1, 12000);
ALTER TABLE `tbl_pembelian` ADD PRIMARY KEY (`kd_pembelian`), ADD KEY `fk_pembelian_barang` (`kd_barang`), ADD KEY `fk_pembelian_user` (`id_user`);

-- Struktur dan data untuk tbl_penjualan
CREATE TABLE `tbl_penjualan` ( `nota` varchar(20) NOT NULL, `kd_barang` varchar(10) NOT NULL, `id_user` varchar(10) NOT NULL, `tgl_penjualan` date NOT NULL, `jmh_barang_jual` int(11) NOT NULL, `total_jual` int(11) NOT NULL ) ENGINE=InnoDB;
INSERT INTO `tbl_penjualan` (`nota`, `kd_barang`, `id_user`, `tgl_penjualan`, `jmh_barang_jual`, `total_jual`) VALUES ('NT1750156271473', 'BRG001', 'USER001', '2025-06-17', 2, 36000);
ALTER TABLE `tbl_penjualan` ADD PRIMARY KEY (`nota`), ADD KEY `fk_penjualan_barang` (`kd_barang`), ADD KEY `fk_penjualan_user` (`id_user`);

-- Membuat relasi FOREIGN KEY
ALTER TABLE `tbl_user` ADD CONSTRAINT `fk_user_level` FOREIGN KEY (`id_level`) REFERENCES `tbl_level` (`id_level`);
ALTER TABLE `tbl_pembelian` ADD CONSTRAINT `fk_pembelian_barang` FOREIGN KEY (`kd_barang`) REFERENCES `tbl_barang` (`kd_barang`), ADD CONSTRAINT `fk_pembelian_user` FOREIGN KEY (`id_user`) REFERENCES `tbl_user` (`id_user`);
ALTER TABLE `tbl_penjualan` ADD CONSTRAINT `fk_penjualan_barang` FOREIGN KEY (`kd_barang`) REFERENCES `tbl_barang` (`kd_barang`), ADD CONSTRAINT `fk_penjualan_user` FOREIGN KEY (`id_user`) REFERENCES `tbl_user` (`id_user`);
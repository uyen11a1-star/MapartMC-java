# Kiểm tra phiên bản Fabric

Ngày kiểm tra: 2026-08-24.

Kết quả: Fabric Loader 0.18.4 tồn tại trong trang release chính thức của Fabric Loader. Fabric API là thư viện khác, được phát hành theo chuỗi dạng `0.138.4+1.21.10` cho Minecraft Java 1.21.10; trang Modrinth liệt kê Fabric API hỗ trợ Minecraft Java 1.21.x.

Vì vậy, yêu cầu “Fabric API 0.18.4” không khớp tên phiên bản của Fabric API. Nếu đặt `fabric_api_version=0.18.4`, Gradle sẽ không tìm thấy artifact Fabric API tương ứng hoặc sẽ không tương thích với 1.21.10. Bản project hiện giữ Fabric API `0.138.4+1.21.10` và dùng Fabric Loader `0.19.3`.

Nguồn kiểm tra:
- Fabric API trên Modrinth: https://modrinth.com/mod/fabric-api/versions
- Fabric Loader release 0.18.4: https://github.com/FabricMC/fabric-loader/releases/tag/0.18.4

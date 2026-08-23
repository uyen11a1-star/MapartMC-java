# RiftAncient.mod — Thiết kế tổng thể

## Bản sắc mod

RiftAncient.mod đưa người chơi vào **Aethel-Ruinium**, một cõi phế tích cổ bị xé đôi bởi khe nứt thời không. Mục tiêu thiết kế là tạo cảm giác khám phá một nền văn minh đã biến mất: đá bazan tím đen, vàng cổ phủ bụi, rune phát sáng, cột đổ nát, đền trung tâm và các vùng địa hình dị thường. Tất cả tài nguyên hình ảnh do mod tạo hoặc tự vẽ, không phụ thuộc texture bên thứ ba.

## Vòng lặp tiến trình

Người chơi khai thác Riftstone và chế tạo **Rift Sigil**. Sigil được dùng trên một khung cổng làm từ **Ancient Riftstone**, theo bố cục khung hình chữ nhật giống cổng Nether. Khi kích hoạt, các ô bên trong biến thành **Rift Portal** màu tím đồng. Bước qua cổng sẽ đưa người chơi đến Aethel-Ruinium; bước ngược lại trả về chiều ban đầu.

Trong Aethel-Ruinium, người chơi thu thập **Aetherite Shard** từ các quặng và mob **Rift Stalker**, **Ashen Sentinel**. Các vật liệu này dùng để chế bộ giáp, công cụ và vũ khí cấp cao hơn. Ngôi đền **Obsidian Reliquary** nằm gần vùng trung tâm. Tại bệ đá trong đền, người chơi phải cầm **Dawnwake Blade** và tương tác để đánh thức boss **Vorath, the Sleeping Ruin**.

Khi thức dậy, Vorath phát hiệu ứng rung màn hình, vòng rune, bụi linh hồn và tiếng nổ trầm. Boss có các đợt tấn công: mưa mảnh vụn, xung lực khe nứt và triệu hồi Rift Stalker. Khi bị đánh bại, boss rơi **Ruin Heart**. Ruin Heart kết hợp với Aetherite và Nether Star để chế **Severance**, thanh kiếm có sát thương cực cao và kỹ năng chém mở vết nứt không gian, gây sát thương theo đường thẳng và tạo hạt sáng tím.

## Nội dung chính

| Nhóm | Thành phần | Vai trò |
| --- | --- | --- |
| Cổng | Ancient Riftstone, Rift Portal, Rift Sigil | Mở lối vào Aethel-Ruinium |
| Khối | Riftstone, Runic Bricks, Aetherite Ore, Aetherite Block, Temple Altar | Xây dựng, trang trí, tiến trình boss |
| Vật phẩm | Aetherite Shard, Dawnwake Blade, Ruin Heart, Severance | Chế tạo và chiến đấu |
| Trang bị | Aetherite armor, pickaxe, axe, shovel, hoe | Bền, mạnh, có hiệu ứng hỗ trợ |
| Mob | Rift Stalker, Ashen Sentinel, Vorath | Kẻ thù thường và boss |
| Công trình | Obsidian Reliquary, Fallen Rune Gate, Ruin Pillars | Khám phá và chiến đấu |
| Hiệu ứng | Rift slash, awakening ring, boss aura | Phản hồi trực quan và cảm giác sức mạnh |

## Công thức

**Rift Sigil**: 4 Aetherite Shard ở bốn góc, 1 Echo Shard ở giữa, 4 Crying Obsidian ở các cạnh.

**Dawnwake Blade**: 2 Aetherite Ingot xếp dọc trên 1 Netherite Sword.

**Aetherite Ingot**: 9 Aetherite Shard.

**Severance**: Ruin Heart ở giữa, Nether Star phía trên, 2 Aetherite Ingot ở hai bên, 2 Echo Shard phía dưới và 1 Netherite Sword ở đáy giữa.

**Aetherite armor/công cụ**: dùng Aetherite Ingot theo các khuôn giáp và công cụ tương ứng. Bộ giáp có độ bền cao, knockback resistance và hiệu ứng Resistance ngắn khi người mặc nhận sát thương.

## Kỹ thuật

Chiều không gian được khai báo bằng JSON datapack `data/riftancient/dimension/aethel_ruinium.json` và `dimension_type/aethel_ruinium.json`, với generator noise riêng và biome tùy biến theo palette cổ đại. Cổng được xử lý bằng block tùy biến: khung Ancient Riftstone được dò theo bố cục giống Nether, Rift Sigil kích hoạt khung, các block bên trong biến thành Rift Portal và block portal teleport entity khi bước vào.

Để giảm rủi ro tương thích, boss và mob sử dụng lớp entity tùy chỉnh dựa trên các vanilla hostile mob ổn định; client renderer tái sử dụng renderer vanilla tương ứng nhưng thay đổi scale/màu/hiệu ứng bằng lớp render đơn giản. Đền trung tâm được tạo bằng `PlacedFeature`/structure-like procedural placement khi chunk gần spawn được tạo, nhờ đó không cần file schematic bên thứ ba. Loot table, recipe, tags, language, models và blockstates đều nằm trong resource/data pack.

## Nhận diện phát hành

- Mod ID: `riftancient`.
- Tên hiển thị: `RiftAncient.mod`.
- Phiên bản ban đầu: `1.0.0`.
- Tác giả: `nguyenquochuy`.
- License: MIT.
- Mục tiêu nền tảng: Fabric Loader + Fabric API cho Minecraft Java 1.21.10.
- Ghi chú phát hành phải nêu yêu cầu Fabric API, Java 21 và cảnh báo sao lưu thế giới khi thử bản đầu tiên.

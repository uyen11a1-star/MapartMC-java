# RiftAncient.mod

**RiftAncient.mod 1.2.1** là mod Fabric cho **Minecraft Java 1.21.10**, được tạo bởi **nguyenquochuy**. Mod mở ra chiều không gian cổ đại **Aethel-Ruinium**, nơi đền thờ bị xé nát bởi khe nứt thời không, các obelisk trôi giữa đá rune và những sinh vật canh giữ lõi Aetherite.

## Yêu cầu

| Thành phần | Phiên bản |
| --- | --- |
| Minecraft Java | 1.21.10 |
| Mod loader | Fabric Loader 0.18.4 trở lên |
| Thư viện | Fabric API 0.138.4+1.21.10 trở lên |
| Java | 21 trở lên |
| License | MIT |

Cài [Fabric Loader](https://fabricmc.net/use/installer/) và [Fabric API](https://modrinth.com/mod/fabric-api), sau đó đặt `RiftAncient-1.2.2.jar` vào thư mục `mods`. Vào Creative Inventory và chọn tab **RiftAncient** để thấy toàn bộ item/block; ô tìm kiếm có thể dùng các ID như `riftancient:rift_sigil`. Đây là mod dành cho Java Edition; không cài trên Bedrock Edition.

## Mở cổng Rift

Dựng khung cổng rộng 4 block và cao 5 block bằng **Ancient Riftstone**, để trống phần trong; khung có thể quay theo cả hai hướng X hoặc Z. Cầm **Rift Sigil** rồi tương tác với block Ancient Riftstone ở góc dưới bên trái của khung. Cổng sẽ tạo mặt rift tím-cyan và cho phép đi hai chiều giữa Overworld và Aethel-Ruinium.

## Aethel-Ruinium

Chiều không gian có palette riêng gồm Ancient Riftstone, Runic Bricks, Aetherite Ore, Aetherite Block và các rune tím-cyan. Địa hình dùng nền đá rune riêng thay vì block Overworld. Cụm công trình quanh điểm vào gồm Obsidian Reliquary, các Rune Obelisk, Fallen Arch và Aetherite Shrine. Biome có màu trời/fog riêng, mob riêng và ambience cổ đại OGG do mod tạo.

## Boss và nghi thức thức tỉnh

Trong **Obsidian Reliquary**, đặt **Dawnwake Blade** lên Reliquary Altar. Nghi thức kéo dài khoảng 100 tick: bệ tế phát vòng rune, lõi boss co giãn, cánh đá chuyển động, hạt reverse portal xoay quanh thân và âm thanh thức tỉnh tăng dần. Sau đó **Vorath, the Sleeping Ruin** thức dậy.

Vorath là entity riêng của RiftAncient, có model/texture riêng, 600 máu, giáp cao, AI truy đuổi, cận chiến, đòn **Rift Burst** tầm xa và giai đoạn **Enraged** khi còn dưới 35% máu. Trong lúc chiến đấu, Vorath hiển thị boss bar trên HUD và đồng bộ trạng thái Enraged khi còn ít máu. Khi bị đánh bại, Vorath rơi **Ruin Heart**.

## Mob và chiến đấu

**Rift Stalker** là entity riêng với model bốn chân, lõi cyan, animation bước đi/nhảy và khả năng teleport ngắn để né đòn. **Ashen Sentinel** là chiến binh phụ xuất hiện trong chiều không gian. Mob cổ đại rơi Aetherite Shard; chín shard ghép thành một Aetherite Ingot.

Dùng Ruin Heart, Nether Star, hai Aetherite Ingot, hai Echo Shard và Netherite Sword để chế **Severance**. Kiếm có sát thương rất cao, glint vĩnh viễn và hai lớp kỹ năng. Đánh thường tạo vệt reverse portal; dùng chuột phải tạo cung chém không gian phía trước, gây damage lan, knockback, particle tím-cyan, âm thanh quét kiếm và cooldown 80 tick.

## Trang bị

Bộ giáp Aetherite và các công cụ Aetherite có texture riêng. Khi mặc đủ bốn mảnh giáp trong Aethel-Ruinium, người chơi nhận Resistance và Night Vision định kỳ; khi cầm công cụ Aetherite, người chơi nhận thêm Haste. Công thức đầy đủ nằm trong `data/riftancient/recipe/` và phần hướng dẫn nguyên liệu ở thư mục source.

## Tài nguyên hình ảnh và âm thanh

Texture entity Vorath, Rift Stalker và rune glow nằm trong `assets/riftancient/textures/entity/`. Rig 3D chi tiết tương thích Blockbench nằm tại `models/blockbench/vorath.bbmodel`, gồm thân giáp, ba mặt nạ, sừng, vai giáp, cánh phân đoạn, chân giáp, lõi và vòng rune. Concept art cổ đại nằm trong `art/`. Ambience Aethel-Ruinium nằm trong `assets/riftancient/sounds/aethel_ambient.ogg` và được gọi qua `sounds.json`.

## Ghi chú phát hành

Bản 1.2.2 giữ toàn bộ tính năng 1.2.1 và đổi Fabric Loader tương thích xuống 0.18.4 theo yêu cầu; Fabric API vẫn là `0.138.4+1.21.10`. Nên sao lưu world trước khi thử chiều không gian hoặc nghi thức boss. Mod không phải sản phẩm của Mojang và không được Mojang phê duyệt hay liên kết.

## Tác giả và giấy phép

Copyright (c) 2026 nguyenquochuy. Mã nguồn và tài sản do tác giả tạo được phân phối theo [MIT License](LICENSE). Minecraft là thương hiệu của Mojang Studios; RiftAncient.mod là dự án không chính thức.

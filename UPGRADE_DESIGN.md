# RiftAncient.mod — Upgrade Design 1.1.0

## Mục tiêu

Bản nâng cấp chuyển prototype thành trải nghiệm dị giới cổ đại có nhân vật riêng và vòng lặp boss rõ ràng. Aethel-Ruinium sẽ không còn chỉ là mặt phẳng đơn sắc; nó có nền đá rune, các vùng đảo nổi, hồ khe nứt và các phế tích phân bố theo seed.

## Boss Vorath

Vorath là một `VorathEntity` riêng thay cho Wither đổi tên. Nó có thân giáp đá đen, lõi tím, ba mặt rune, 600 máu, AI truy đuổi người chơi, đòn nổ rune ở tầm xa, đòn cận chiến, giai đoạn enraged dưới 35% máu và trạng thái `SLEEPING`, `AWAKENING`, `ACTIVE`, `ENRAGED`. Bệ tế truyền `AWAKENING` trong khoảng 100 tick; renderer client hiển thị model co giãn, vòng rune và lõi sáng trước khi chuyển sang `ACTIVE`.

## Rift Stalker

Rift Stalker là `RiftStalkerEntity` riêng với model bốn chân, lõi cyan và animation nhảy. Nó teleport ngắn khi bị đánh, né đòn và rơi Aetherite Shard/Rift Fang. Ashen Sentinel vẫn được dùng như một chiến binh phụ dựa trên Husk nhưng có trang bị và tên riêng.

## Vũ khí Severance

Severance giữ sát thương cao, nhưng bổ sung cooldown kỹ năng. Đánh thường vẽ một đường cắt hạt tím; dùng chuột phải tạo `RiftSlash` hình cung trước mặt, gây damage lan, knockback và cooldown 80 tick. Khi Vorath chết, Ruin Heart tiếp tục là nguyên liệu lõi.

## Công trình và worldgen

Một Obsidian Reliquary lớn được dựng ở spawn. Các micro-ruin gồm Rune Obelisk, Fallen Arch và Aetherite Shrine được rải theo khoảng cách an toàn quanh người chơi ở Aethel-Ruinium. Generator giữ palette Ancient Riftstone/Runic Bricks và bổ sung đảo cao thấp để khám phá có nhịp điệu.

## Art direction

Tất cả texture mới theo phong cách pixel-art cổ đại: obsidian đen xanh, rune tím-magenta, lõi cyan và điểm nhấn vàng đồng. Texture entity đặt tại `assets/riftancient/textures/entity/`; concept art đặt trong `art/` để dùng làm ảnh dự án.

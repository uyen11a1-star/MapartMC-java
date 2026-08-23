# Nghiên cứu nâng cấp entity/rendering

Fabric Wiki và Fabric Documentation đều hướng dẫn tách logic entity phía server khỏi renderer/model phía client. Entity custom cần EntityType đăng ký bằng ResourceKey; entity thường mở rộng PathfinderMob; renderer được đăng ký trong ClientModInitializer; model dùng ModelPart và texture riêng. Tài liệu mới cũng có render state riêng và lớp animation riêng.

Nguồn chính thức:

- Fabric Wiki — Creating an Entity: https://wiki.fabricmc.net/tutorial:entity
- Fabric Documentation — Creating Your First Entity: https://docs.fabricmc.net/develop/entities/first-entity
- Tài liệu Fabric hiện hành cho trang entity hiển thị bản docs mới dùng Mojang mappings và nhắc Blockbench phải khớp mappings khi xuất model.

Kế hoạch kỹ thuật: chuyển Vorath từ Wither được đổi tên thành entity riêng dựa trên PathfinderMob/Mob, tạo một entity Rift Stalker riêng, đăng ký renderer client và model thủ công bằng ModelPart để không cần phụ thuộc file model độc quyền. Animation sẽ dùng state/tick của entity, với các trạng thái ngủ, thức tỉnh, tấn công và cooldown kỹ năng; client renderer vẽ thêm vòng rune/hạt không gian trong các trạng thái tương ứng.

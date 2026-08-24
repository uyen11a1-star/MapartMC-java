# Asset audit

Contact sheet `asset_sheet.png` cho thấy nhiều item hiện tại dùng hình ảnh tím-cyan giống nhau, khó phân biệt trong inventory; texture block rất tối và một số gần như không có chi tiết. Vì vậy cần thay toàn bộ texture item/block bằng bộ pixel-art riêng, có silhouette và màu sắc khác nhau, đồng thời giữ đúng đường dẫn model JSON.

Dimension hiện tại dùng generator flat với các lớp bedrock/runic bricks/ancient riftstone; cần bổ sung terrain generator rõ ràng và bảo đảm spawn trên nền block solid. Portal phải chỉ được tạo khi người chơi dùng Rift Sigil trên góc khung Ancient Riftstone, không đặt portal/khối sẵn trong Overworld.

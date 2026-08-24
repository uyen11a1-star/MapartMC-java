# RiftAncient 1.4.0 — Resource/schema audit

Ngày kiểm chứng: 2026-08-24.

## Minecraft 1.21.10 item model definition

Đã đọc trực tiếp `minecraft-client.jar` của Loom cache 1.21.10. Vanilla có các file `assets/minecraft/items/*.json`. Mẫu `assets/minecraft/items/diamond.json` là:

```json
{
  "model": {
    "type": "minecraft:model",
    "model": "minecraft:item/diamond"
  }
}
```

Các file mới trong `src/main/resources/assets/riftancient/items/*.json` dùng đúng schema và trỏ tới model `riftancient:item/<name>`.

## Flat generator schema

Đã đọc trực tiếp preset vanilla 1.21.10. `classic_flat.json` dùng `features: false`, `lakes: false`, `layers: [...]`, và `structure_overrides` là danh sách hoặc tag hợp lệ. Preset `the_void.json` cũng dùng `lakes: false`; vì vậy `aethel_ruinium.json` đã sửa từ `lakes: []` thành `lakes: false`, giữ `structure_overrides: []`.

## Client resource reload

Lệnh kiểm chứng: `xvfb-run -a ./gradlew runClient --no-daemon` trên source hiện tại.

Kết quả: Minecraft 1.21.10 chạy với Fabric Loader 0.18.4, mod `riftancient 1.3.0` ở lần chạy trước khi bump version. Resource reload hoàn tất; bộ lọc log không tìm thấy `missing texture`, `missing model`, `failed to load model`, `unable to load`, `ResourceLocationException`, hoặc `FileNotFoundException` liên quan resource. Đây là bằng chứng item model definitions mới đã loại lỗi model/icon missing ở bước reload.

Các lỗi còn lại chỉ thuộc môi trường headless: OpenAL/ALSA không có thiết bị âm thanh, Yggdrasil/Realms không xác thực được. Chúng không phải lỗi texture/model.

## Server smoke test

Server 1.21.10 + Fabric Loader 0.18.4 đã khởi động và load dimension key `riftancient:aethel_ruinium`; log lưu rằng level này được save. Các lệnh `execute in ...` từ stdin của dev server trả generic unexpected error ngay cả với dimension Nether vanilla, nên probe command này không phải phép kiểm chứng hợp lệ cho generator. Cần dùng test level/chunk hoặc client actual world ở phase 2.

## Portal và dimension integration probe

Đã chèn probe server tạm thời, build và chạy `/riftprobe` trên Fabric Loader 0.18.4/Minecraft 1.21.10, sau đó xoá probe khỏi source phát hành.

Kết quả log:

```text
RIFT_PROBE dimension=riftancient:aethel_ruinium y0=minecraft:bedrock y1=riftancient:runic_bricks y4=riftancient:runic_bricks y5=riftancient:ancient_riftstone altar=riftancient:temple_altar
RIFT_PROBE portalOpened=true portalBlocks=6 expected=6 teleportDimension=riftancient:aethel_ruinium teleportPosition=(0.5, 6.0, 0.5)
```

Probe đã dựng đúng khung 4×5 bằng Ancient Riftstone, gọi chính `RiftPortalBlock.createFrame`, đếm được 6 block portal ở phần rỗng, sau đó gọi chính `getPortalDestination`. Điều này xác nhận dimension có nền vật lý, đền ở tâm (0,5,0), và transition Overworld → Aethel-Ruinium không còn trả null.

## Final client run 1.4.0

Sau khi xoá probe, clean build và chạy lại client. Log ghi `riftancient 1.4.0`, `Loading Minecraft 1.21.10 with Fabric Loader 0.18.4`, resource reload và texture atlases thành công. Bộ lọc không tìm thấy lỗi missing texture/model hoặc lỗi parse model. OpenAL/ALSA vẫn báo không có audio device trong môi trường headless, nhưng client tiếp tục reload resource; đây là giới hạn sandbox, không phải lỗi asset.

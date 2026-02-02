# Embroidery Studio (Android)

This repository is a starter scaffold for an Android application that:

1. Opens common embroidery files (DST, PES, JEF, EXP, VP3, etc.).
2. Provides **true view** (filled shapes) and **stitch view** (needle path).
3. Converts between formats (e.g., DST → PES).
4. Displays design metadata (size, stitch count, color changes, stops).

## Suggested approach

- **Parsing + conversion**: integrate a library such as:
  - [EmbroideryIO](https://github.com/EmbroidePy/embroideryio) (Java-based), or
  - [libembroidery](https://github.com/Embroidermodder/libembroidery) via JNI.
- **Rendering**:
  - Use a custom `Bitmap` renderer for stitch view (draw lines and jumps).
  - Use an offscreen canvas + fill logic for true view.
- **Storage**:
  - Use `ContentResolver` + SAF for file import/export.

## Project layout

- `app/src/main/java/com/ansa/embroidery/data`: file parsing and design models.
- `app/src/main/java/com/ansa/embroidery/render`: render interfaces for true/stitch view.
- `app/src/main/java/com/ansa/embroidery/ui`: view model and view state objects.

## Next steps

1. Implement `EmbroideryParser` using your chosen library.
2. Add a `DesignSurfaceView` or `Compose` canvas for rendering.
3. Wire file pickers to load, preview, and export designs.
4. Add tests for format support and metadata extraction.

## Build / assemble

From the repository root:

```sh
./gradlew assembleDebug
```

The APK will be generated at:

```
app/build/outputs/apk/debug/app-debug.apk
```

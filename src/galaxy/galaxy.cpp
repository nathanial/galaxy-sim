#include "galaxy.hpp"
#include <iostream>
#include <QImage>


#include "SkData.h"
#include "SkImage.h"
#include "SkStream.h"
#include "SkSurface.h"
#include "SkPaint.h"
#include "SkPath.h"
#include "SkCanvas.h"

using namespace galaxy;

static int count;

#define CORRECT_COLOR(R,G,B,A) SkColorSetARGBInline(A, B, G, R)

static void draw(SkCanvas *canvas){
    std::cout << "Draw" << std::endl;
    const SkScalar scale = 256.0f;
    const SkScalar R = 0.45f * scale;
    const SkScalar TAU = 6.2831853f;
    SkPath path;
    path.moveTo(R, 0.0f);
    for (int i = 1; i < 7; ++i) {
        SkScalar theta = 3 * i * TAU / 7;
        path.lineTo(R * cos(theta), R * sin(theta));
    }
    path.close();
    SkPaint p;
    p.setColor(CORRECT_COLOR(100, 0, 255, 255));
    p.setAntiAlias(true);
    canvas->clear(SK_ColorWHITE);
    canvas->translate(0.5f * scale, 0.5f * scale);
    canvas->rotate(count++);
    canvas->drawPath(path, p);
}

namespace galaxy {

    std::vector<unsigned char> render(int width, int height){
        SkImageInfo info = SkImageInfo::Make(width, height,kBGRA_8888_SkColorType, kPremul_SkAlphaType);
        size_t rowBytes = info.minRowBytes();
        size_t size = info.getSafeSize(rowBytes);
        std::vector<unsigned char> pixelMemory(size);  // allocate memory
        SkAutoTUnref<SkSurface> surface(SkSurface::NewRasterDirect(info, pixelMemory.data(), rowBytes));
        SkCanvas* canvas = surface->getCanvas();
        draw(canvas);
        return pixelMemory;
    }
}


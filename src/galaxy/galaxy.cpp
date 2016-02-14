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

#define CORRECT_COLOR(R,G,B,A) SkColorSetARGBInline(A, B, G, R)

static const int stars = 10000;


Galaxy::Galaxy(){
  for(int i = 0; i < stars; i++){
    this->colors.push_back({rand() % 255, rand() % 255, rand() % 255, 255});
    this->speeds.push_back(rand() * 2 / 10000000.0);
  }
}

ImageBuffer Galaxy::render(const DrawOptions &options){
  SkImageInfo info = SkImageInfo::Make(options.width, options.height, kBGRA_8888_SkColorType, kPremul_SkAlphaType);
  size_t rowBytes = info.minRowBytes();
  size_t size = info.getSafeSize(rowBytes);
  std::vector<unsigned char> pixelMemory(size);  // allocate memory
  SkAutoTUnref<SkSurface> surface(SkSurface::NewRasterDirect(info, pixelMemory.data(), rowBytes));
  SkCanvas* canvas = surface->getCanvas();
  this->draw(canvas, options);
  return pixelMemory;
}

void Galaxy::draw(SkCanvas *canvas, const DrawOptions &options){
  const SkScalar scale = 30.0f;
  const SkScalar R = 0.45f * scale;
  const SkScalar TAU = 6.2831853f;
  SkPaint p;
  p.setAntiAlias(true);
  canvas->clear(SK_ColorBLACK);
  auto columns = 500;

  for(int i = 0; i < stars; i++){
    canvas->save();
    SkPath path;
    path.moveTo(R, 0.0f);
    auto sides = ((int)speeds[i]) % 5 + 5;
    for (int j = 1; j < sides; ++j) {
      SkScalar theta = 3 * j * TAU / sides;
      path.lineTo(R * cos(theta), R * sin(theta));
    }
    path.close();

    canvas->translate(options.translateX, options.translateY);
    canvas->scale(options.scaleX, options.scaleY);
    canvas->translate(0.5f * scale + (i % columns) * scale, 0.5f * scale + (i / columns) * scale);
    canvas->rotate(this->count * i);

    Color color = this->colors[i];
    p.setColor(CORRECT_COLOR(color.r, color.g, color.b, color.a));
    canvas->drawPath(path, p);
    canvas->restore();
  }
  this->count += 1;
}

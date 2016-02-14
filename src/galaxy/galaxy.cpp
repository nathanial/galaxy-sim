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

#include <math.h>

using namespace galaxy;

#define CORRECT_COLOR(R,G,B,A) SkColorSetARGBInline(A, B, G, R)

static const int stars = 10000;

static double radians(double degrees){
  return (degrees * M_PI) / 180;
}

static double spiral(double t){
  double a = 500;
  double b = 0.5;
  return a * exp(b * t);
}

Galaxy::Galaxy(){
  for(int arm = 0; arm < 4; arm ++){
    double armAngle = radians(arm * 90);
    for(int theta = 0; theta < 500; theta += 1){
      SolarSystem system;
      system.star.color = { 255, 255, 255, 255 };

      double t = radians(theta);
      double x = spiral(t) * cos(t);
      double y = spiral(t) * sin(t);


      double rx = x * cos(armAngle) - y*sin(armAngle);
      double ry = x * sin(armAngle) + y*cos(armAngle);

      system.star.radius = 5;
      system.x = rx;
      system.y = ry;
      this->solarSystems.push_back(system);
    }
  }
  std::cout << "Solar Systems " << this->solarSystems.size() << std::endl;

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

  canvas->clear(SK_ColorBLACK);

  canvas->translate(options.translateX, options.translateY);
  canvas->scale(options.scaleX, options.scaleY);

  for(auto && system : solarSystems){
    canvas->save();

    system.draw(canvas);

    canvas->restore();
  }

}

void SolarSystem::draw(SkCanvas *canvas){
  const SkScalar scale = 60.0f;
  const SkScalar R = 0.45f * scale;
  const SkScalar TAU = 6.2831853f;
  SkPaint p;
  p.setAntiAlias(true);

  SkPath path;
  path.moveTo(R, 0.0f);

  auto sides = 5;
  for (int j = 1; j < sides; ++j) {
    SkScalar theta = 3 * j * TAU / sides;
    path.lineTo(R * cos(theta), R * sin(theta));
  }
  path.close();

  canvas->translate(this->x, this->y);

  p.setColor(CORRECT_COLOR(star.color.r, star.color.g, star.color.b, star.color.a));
  canvas->drawPath(path, p);
}

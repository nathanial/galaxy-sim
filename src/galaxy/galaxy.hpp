#pragma once

#include <memory>
#include <vector>
#include "SkRect.h"

class SkCanvas;
class QImage;

namespace galaxy {

  struct Color {
    int r, g, b, a;
  };


  class StarGradient {
  public:
    StarGradient();
    Color randomColor();
  private:
    std::unique_ptr<QImage> gradientImage;
  };

  struct DrawOptions {
    double translateX = 0, translateY = 0;
    double scaleX = 1, scaleY = 1;
    int width;
    int height;
  };

  struct Star {
    Color color;
    double radius;
  };

  struct Planet {
    double x,y;
    double orbitRadius;
    double radius;
  };

  struct SolarSystem {
    Star star;
    double x, y;
    std::vector<Planet> planets;
    void draw(SkCanvas *canvas);
  };

  typedef std::vector<unsigned char> ImageBuffer;

  class Galaxy {

  public:
    Galaxy();
    ImageBuffer render(const DrawOptions& options);

  private:
    std::vector<SolarSystem> solarSystems;
    std::vector<SkRect> clouds;
    void draw(SkCanvas *canvas, const DrawOptions& options);
  };

  typedef std::shared_ptr<Galaxy> GalaxyPtr;

}

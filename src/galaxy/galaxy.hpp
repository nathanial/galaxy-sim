#ifndef GALAXY_H
#define GALAXY_H

#include <memory>
#include <vector>

class SkCanvas;

namespace galaxy {

  struct DrawOptions {
    double translateX = 0, translateY = 0;
    double scaleX = 1, scaleY = 1;
    int width;
    int height;
  };

  struct Color {
    int r, g, b, a;
  };


  typedef std::vector<unsigned char> ImageBuffer;

  class Galaxy {

  public:
    Galaxy();
    ImageBuffer render(const DrawOptions& options);

  private:
    std::vector<Color> colors;
    std::vector<double> speeds;
    DrawOptions drawOptions;
    int count = 0;

    void draw(SkCanvas *canvas, const DrawOptions& options);
  };

  typedef std::shared_ptr<Galaxy> GalaxyPtr;

}

#endif

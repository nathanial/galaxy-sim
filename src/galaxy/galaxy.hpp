#ifndef GALAXY_H
#define GALAXY_H

#include <memory>
#include <vector>

class SkCanvas;

namespace galaxy {

struct Color {
  int r, g, b, a;
};


typedef std::vector<unsigned char> ImageBuffer;

class Galaxy {
public:
  Galaxy();
  ImageBuffer render(int width, int height);
private:
  std::vector<Color> colors;
  std::vector<double> speeds;
  int count = 0;

  void draw(SkCanvas *canvas);
};

typedef std::shared_ptr<Galaxy> GalaxyPtr;

}

#endif

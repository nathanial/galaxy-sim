#ifndef GALAXY_H
#define GALAXY_H

#include <memory>
#include <vector>

namespace galaxy {

  class StarMap {

  };

  typedef std::shared_ptr<StarMap> StarMapPtr;

  class Galaxy {
  private:
    StarMapPtr starMap;
  };

  typedef std::shared_ptr<Galaxy> GalaxyPtr;
  typedef std::shared_ptr<unsigned char []> ImageBuffer;
  typedef std::vector<ImageBuffer> ImageBuffers;

  static void render(ImageBuffer buffer, int width, int height);

}

#endif

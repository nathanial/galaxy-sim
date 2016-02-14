#ifndef GALAXYVIEW_H
#define GALAXYVIEW_H

#include <QWidget>
#include <memory>
#include "galaxy.hpp"


class QPaintEvent;
class QMouseEvent;

class GalaxyView : public QWidget
{
  Q_OBJECT

public:
  explicit GalaxyView(QWidget *parent = 0);

protected:
  void paintEvent(QPaintEvent *event) Q_DECL_OVERRIDE;
  void mouseMoveEvent(QMouseEvent * event) Q_DECL_OVERRIDE;
  void mousePressEvent(QMouseEvent * event) Q_DECL_OVERRIDE;
  void mouseReleaseEvent(QMouseEvent *event) Q_DECL_OVERRIDE;

private:
  galaxy::GalaxyPtr galaxy;
  galaxy::DrawOptions options;
  bool dragging = false;
  double startX = 0, startY = 0;
  double startTranslateX = 0, startTranslateY = 0;
  void render(QPaintEvent *event);

};

#endif // GALAXYVIEW_H

#include <iostream>
#include "galaxyview.h"
#include "galaxy.hpp"
#include <QPainter>
#include <QTimer>
#include <QPaintEvent>
#include <QResizeEvent>
#include <QDesktopWidget>

GalaxyView::GalaxyView(QWidget *parent) :
  QWidget(parent),
  galaxy(new galaxy::Galaxy()){

  this->setAttribute(Qt::WA_OpaquePaintEvent);
  this->setAttribute(Qt::WA_NoSystemBackground);
  this->setMouseTracking(true);

  QTimer *timer = new QTimer(this);
  connect(timer, SIGNAL(timeout()), this, SLOT(repaint()));
  timer->start(16);
}

void GalaxyView::paintEvent(QPaintEvent *event){
  this->render(event);
}

void GalaxyView::mouseMoveEvent(QMouseEvent * event) {
  if(this->dragging){
    this->options.translateX = (this->startTranslateX + (event->x() - this->startX));
    this->options.translateY = (this->startTranslateY + (event->y() - this->startY));
  }
}

void GalaxyView::mousePressEvent(QMouseEvent * event) {
  this->startX = event->x();
  this->startY = event->y();
  this->startTranslateX = this->options.translateX;
  this->startTranslateY = this->options.translateY;
  this->dragging = true;
}

void GalaxyView::mouseReleaseEvent(QMouseEvent *event) {
  this->dragging = false;
}

void GalaxyView::wheelEvent(QWheelEvent *event) {
  std::cout << "Mouse X,Y " << event->x() << "," << event->y() << std::endl;
  galaxy::DrawOptions& opts = this->options;

  auto oldScaleX = opts.scaleX;
  auto oldScaleY = opts.scaleY;

  if(event->delta() > 0){
    opts.scaleX *= 1.15;
    opts.scaleY *= 1.15;
  } else {
    opts.scaleX *= 0.85;
    opts.scaleY *= 0.85;
  }

  auto deltaWidth = (opts.scaleX * opts.width) - (oldScaleX * opts.width);
  auto deltaHeight = (opts.scaleY * opts.height) - (oldScaleY * opts.height);
  auto qx = (event->x() / (double) opts.width);
  auto qy = (event->y() / (double) opts.height);

  auto sx = (event->x() - opts.translateX) / oldScaleX;
  auto sy = (event->y() - opts.translateY) / oldScaleY;

  auto ax = sx / (double) this->width();
  auto ay = sy / (double) this->height();

  auto kx = (deltaWidth * ax); //needs to shrink as
  auto ky = (deltaHeight * ay);

  opts.translateX = opts.translateX - kx;
  opts.translateY = opts.translateY - ky;
}

void GalaxyView::render(QPaintEvent *event){
  const QRect & rect = event->rect();
  galaxy::GalaxyPtr g(new galaxy::Galaxy);

  options.width = this->width();
  options.height = this->height();

  auto buffer = this->galaxy->render(options);

  QImage image(buffer.data(), rect.width(), rect.height(), rect.width() * 4, QImage::Format_RGBA8888);
  QPainter painter(this);
  painter.drawImage(QPointF(0,0), image);
}

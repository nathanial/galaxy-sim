#include <iostream>
#include "mainwindow.h"
#include "ui_mainwindow.h"
#include <QPainter>
#include <QTimer>
#include <QPaintEvent>
#include <QResizeEvent>
#include "galaxy.hpp"
#include <stdlib.h>

//AGG_BGR24

MainWindow::MainWindow(QWidget *parent) :
  QMainWindow(parent),
  ui(new Ui::MainWindow),  galaxy(new galaxy::Galaxy())
{
  ui->setupUi(this);
  this->setAttribute(Qt::WA_OpaquePaintEvent);
  this->setAttribute(Qt::WA_NoSystemBackground);

  QTimer *timer = new QTimer(this);
  connect(timer, SIGNAL(timeout()), this, SLOT(repaint()));
  timer->start(16);
}

MainWindow::~MainWindow(){
  delete ui;
}

void MainWindow::resizeEvent(QResizeEvent *event){
  const QSize& size = event->size();
}

void MainWindow::paintEvent(QPaintEvent *event){
  this->render(event);
}

void MainWindow::render(QPaintEvent *event){
  const QRect & rect = event->rect();
  galaxy::GalaxyPtr g(new galaxy::Galaxy);

  auto buffer = this->galaxy->render(this->width(), this->height());


  QImage image(buffer.data(), rect.width(), rect.height(), rect.width() * 4, QImage::Format_RGBA8888);
  QPainter painter(this);
  painter.drawImage(QPointF(0,0), image);
}

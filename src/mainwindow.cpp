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
    ui(new Ui::MainWindow){
    ui->setupUi(this);
    this->setAttribute(Qt::WA_OpaquePaintEvent);
    this->setAttribute(Qt::WA_NoSystemBackground);
    std::cout << this->width() << " " << this->height() << std::endl;
    this->aggBuffer.reset(new unsigned char[this->width() * this->height() * 3]);

    QTimer *timer = new QTimer(this);
    connect(timer, SIGNAL(timeout()), this, SLOT(repaint()));
    timer->start(16);
}

MainWindow::~MainWindow(){
    delete ui;
}

void MainWindow::resizeEvent(QResizeEvent *event){
    const QSize& size = event->size();
    this->aggBuffer.reset(new unsigned char [size.width() * size.height() * 3]);
}

void MainWindow::paintEvent(QPaintEvent *event){
    this->render(event);
}

void MainWindow::render(QPaintEvent *event){
    const QRect & rect = event->rect();
    galaxy::GalaxyPtr g(new galaxy::Galaxy);

    galaxy::render(this->aggBuffer.get(), this->width(), this->height());

    QImage image(this->aggBuffer.get(), rect.width(), rect.height(), rect.width() * 3, QImage::Format_RGB888);
    QPainter painter(this);
    painter.drawImage(QPointF(0,0), image);
}

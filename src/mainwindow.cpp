#include <iostream>
#include "mainwindow.h"
#include "ui_mainwindow.h"
#include <stdlib.h>
#include <QLayout>
#include <QBoxLayout>
#include "galaxy/galaxyview.h"

//AGG_BGR24

MainWindow::MainWindow(QWidget *parent) :
  QMainWindow(parent)
{
  this->setCentralWidget(new GalaxyView());
  new QGridLayout(this->centralWidget());
  this->resize(1000, 1000);

}


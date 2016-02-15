#pragma once

#include <QMainWindow>

class QGridLayout;


class MainWindow : public QMainWindow
{
  Q_OBJECT

public:
  explicit MainWindow(QWidget *parent = 0);

private:
  QGridLayout* gridLayout;
};

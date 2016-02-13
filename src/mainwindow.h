#ifndef MAINWINDOW_H
#define MAINWINDOW_H

#include <QMainWindow>
#include <memory>
#include <vector>

class QImage;

namespace Ui {
  class MainWindow;
}

namespace galaxy {
  class Galaxy;
  typedef std::shared_ptr<Galaxy> GalaxyPtr;
}


class MainWindow : public QMainWindow
{
  Q_OBJECT

public:
  explicit MainWindow(QWidget *parent = 0);
  ~MainWindow();

protected:
  void paintEvent(QPaintEvent *event) Q_DECL_OVERRIDE;
  void resizeEvent(QResizeEvent *event) Q_DECL_OVERRIDE;

private:
  Ui::MainWindow *ui;
  galaxy::GalaxyPtr galaxy;
  void render(QPaintEvent *event);

};

#endif // MAINWINDOW_H

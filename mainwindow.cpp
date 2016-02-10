#include <iostream>
#include "mainwindow.h"
#include "ui_mainwindow.h"
#include "agg_rendering_buffer.h"
#include "agg_rasterizer_scanline_aa.h"
#include "agg_conv_transform.h"
#include "agg_bspline.h"
#include "agg_ellipse.h"
#include "agg_gsv_text.h"
#include "agg_scanline_p.h"
#include "agg_renderer_scanline.h"
#include "agg_pixfmt_rgb.h"
#include "platform/agg_platform_support.h"
#include <QPainter>
#include <QTimer>
#include <QPaintEvent>
#include <QResizeEvent>

#include <stdlib.h>

//AGG_BGR24

const int r1 = rand();
const int r2 = rand();
const int r3 = rand();
int count = 0;


MainWindow::MainWindow(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::MainWindow){
    ui->setupUi(this);
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

    agg::rasterizer_scanline_aa<> pf;
    agg::scanline_p8 sl;

    typedef agg::pixfmt_rgb24 pixfmt;
    typedef agg::renderer_base<pixfmt> renderer_base;


    agg::rendering_buffer rbuf(this->aggBuffer.get(),
                               rect.width(),
                               rect.height(),
                               rect.width() * 3);

    pixfmt pixf(rbuf);
    renderer_base rb(pixf);

    rb.clear(agg::rgba(0,0,0));

    //agg::conv_transform<agg::ellipse> t1(e1, trans_affine_());

    double alpha = 1.0;
//    for(int i = 0; i < 90000; i++){
//        agg::ellipse e1;
//        e1.init((i % 385) * 5 + 5, (i / 385) * 5 + 10, 2, 2, 5);
//        pf.add_path(e1);
//        pf.close_polygon();
//        agg::render_scanlines_aa_solid(
//            pf, sl, rb,
//            agg::rgba(((r1 + i) + count % 1000) / 1000.0,
//                      ((r2 + i) + count % 1000) / 1000.0,
//                      ((r3 + i) - count % 1000) / 1000.0, 1.0));
//    }

    std::cout << "Sine(rand) = " << (sin(rand()) + 1) / 2 << std::endl;

    for(int i = 0; i < rbuf.height() - 3; i += 3){
        for(int j = 0; j < rbuf.width() - 3; j += 3){
          unsigned char* ptr = rbuf.row_ptr(i) + j * 3;
          *ptr++ = 255 * ((sin((i + j + count) / 1000.0) + 1) / 2);
          *ptr++ = 255 * ((sin((i + j + count) / 1000.0 + 100) + 1) / 2);
          *ptr++ = 255 * ((sin((i + j + count) / 1000.0 + 200) + 1) / 2);
        }
    }
    count += rand() / 10000000.0;

    QImage image(this->aggBuffer.get(), rect.width(), rect.height(), rect.width() * 3, QImage::Format_RGB888);
    QPainter painter(this);
    painter.drawImage(QPointF(0,0), image);
}

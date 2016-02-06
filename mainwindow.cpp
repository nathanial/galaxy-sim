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

//AGG_BGR24


static const int frame_width = 320;
static const int frame_height = 200;

MainWindow::MainWindow(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::MainWindow){
    ui->setupUi(this);
    this->aggBuffer = new unsigned char[frame_width * frame_height * 3];
}

MainWindow::~MainWindow(){
    delete ui;
    delete [] this->aggBuffer;
}

void MainWindow::paintEvent(QPaintEvent *event){

    agg::rasterizer_scanline_aa<> pf;
    agg::scanline_p8 sl;

    typedef agg::pixfmt_rgb24 pixfmt;
    typedef agg::renderer_base<pixfmt> renderer_base;


    memset(this->aggBuffer, 255, frame_width * frame_height * 3);

    agg::rendering_buffer rbuf(this->aggBuffer,
                               frame_width,
                               frame_height,
                               frame_width * 3);

    pixfmt pixf(rbuf);
    renderer_base rb(pixf);

    rb.clear(agg::rgba(1,1,1));

    agg::ellipse e1;
    //agg::conv_transform<agg::ellipse> t1(e1, trans_affine_());


    double alpha = 1.0;
    e1.init(50, 50, 5, 5, 8);
    pf.add_path(e1);
    agg::render_scanlines_aa_solid(
        pf, sl, rb,
        agg::rgba(0.5, 0.5, 0.5, 1.0));

    QImage image(this->aggBuffer, frame_width, frame_height, QImage::Format_RGB888);
    QPainter painter(this);
    painter.drawImage(QPointF(0,0), image);
}

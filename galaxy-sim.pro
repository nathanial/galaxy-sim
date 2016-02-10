#-------------------------------------------------
#
# Project created by QtCreator 2016-02-05T02:55:22
#
#-------------------------------------------------

QT       += core gui

greaterThan(QT_MAJOR_VERSION, 4): QT += widgets

TARGET = galaxy-sim
TEMPLATE = app

CONFIG += c++14

INCLUDEPATH += src/agg/include


FORMS    += src/mainwindow.ui

DISTFILES += \
    TODO.txt

HEADERS += \
    src/agg/include/ctrl/agg_bezier_ctrl.h \
    src/agg/include/ctrl/agg_cbox_ctrl.h \
    src/agg/include/ctrl/agg_ctrl.h \
    src/agg/include/ctrl/agg_gamma_ctrl.h \
    src/agg/include/ctrl/agg_gamma_spline.h \
    src/agg/include/ctrl/agg_polygon_ctrl.h \
    src/agg/include/ctrl/agg_rbox_ctrl.h \
    src/agg/include/ctrl/agg_scale_ctrl.h \
    src/agg/include/ctrl/agg_slider_ctrl.h \
    src/agg/include/ctrl/agg_spline_ctrl.h \
    src/agg/include/platform/mac/agg_mac_pmap.h \
    src/agg/include/platform/win32/agg_win32_bmp.h \
    src/agg/include/platform/agg_platform_support.h \
    src/agg/include/util/agg_color_conv.h \
    src/agg/include/util/agg_color_conv_rgb16.h \
    src/agg/include/util/agg_color_conv_rgb8.h \
    src/agg/include/agg_alpha_mask_u8.h \
    src/agg/include/agg_arc.h \
    src/agg/include/agg_array.h \
    src/agg/include/agg_arrowhead.h \
    src/agg/include/agg_basics.h \
    src/agg/include/agg_bezier_arc.h \
    src/agg/include/agg_bitset_iterator.h \
    src/agg/include/agg_blur.h \
    src/agg/include/agg_bounding_rect.h \
    src/agg/include/agg_bspline.h \
    src/agg/include/agg_clip_liang_barsky.h \
    src/agg/include/agg_color_gray.h \
    src/agg/include/agg_color_rgba.h \
    src/agg/include/agg_config.h \
    src/agg/include/agg_conv_adaptor_vcgen.h \
    src/agg/include/agg_conv_adaptor_vpgen.h \
    src/agg/include/agg_conv_bspline.h \
    src/agg/include/agg_conv_clip_polygon.h \
    src/agg/include/agg_conv_clip_polyline.h \
    src/agg/include/agg_conv_close_polygon.h \
    src/agg/include/agg_conv_concat.h \
    src/agg/include/agg_conv_contour.h \
    src/agg/include/agg_conv_curve.h \
    src/agg/include/agg_conv_dash.h \
    src/agg/include/agg_conv_gpc.h \
    src/agg/include/agg_conv_marker.h \
    src/agg/include/agg_conv_marker_adaptor.h \
    src/agg/include/agg_conv_segmentator.h \
    src/agg/include/agg_conv_shorten_path.h \
    src/agg/include/agg_conv_smooth_poly1.h \
    src/agg/include/agg_conv_stroke.h \
    src/agg/include/agg_conv_transform.h \
    src/agg/include/agg_conv_unclose_polygon.h \
    src/agg/include/agg_curves.h \
    src/agg/include/agg_dda_line.h \
    src/agg/include/agg_ellipse.h \
    src/agg/include/agg_ellipse_bresenham.h \
    src/agg/include/agg_embedded_raster_fonts.h \
    src/agg/include/agg_font_cache_manager.h \
    src/agg/include/agg_gamma_functions.h \
    src/agg/include/agg_gamma_lut.h \
    src/agg/include/agg_glyph_raster_bin.h \
    src/agg/include/agg_gradient_lut.h \
    src/agg/include/agg_gsv_text.h \
    src/agg/include/agg_image_accessors.h \
    src/agg/include/agg_image_filters.h \
    src/agg/include/agg_line_aa_basics.h \
    src/agg/include/agg_math.h \
    src/agg/include/agg_math_stroke.h \
    src/agg/include/agg_path_length.h \
    src/agg/include/agg_path_storage.h \
    src/agg/include/agg_path_storage_integer.h \
    src/agg/include/agg_pattern_filters_rgba.h \
    src/agg/include/agg_pixfmt_amask_adaptor.h \
    src/agg/include/agg_pixfmt_gray.h \
    src/agg/include/agg_pixfmt_rgb.h \
    src/agg/include/agg_pixfmt_rgb_packed.h \
    src/agg/include/agg_pixfmt_rgba.h \
    src/agg/include/agg_pixfmt_transposer.h \
    src/agg/include/agg_rasterizer_cells_aa.h \
    src/agg/include/agg_rasterizer_compound_aa.h \
    src/agg/include/agg_rasterizer_outline.h \
    src/agg/include/agg_rasterizer_outline_aa.h \
    src/agg/include/agg_rasterizer_scanline_aa.h \
    src/agg/include/agg_rasterizer_sl_clip.h \
    src/agg/include/agg_renderer_base.h \
    src/agg/include/agg_renderer_markers.h \
    src/agg/include/agg_renderer_mclip.h \
    src/agg/include/agg_renderer_outline_aa.h \
    src/agg/include/agg_renderer_outline_image.h \
    src/agg/include/agg_renderer_primitives.h \
    src/agg/include/agg_renderer_raster_text.h \
    src/agg/include/agg_renderer_scanline.h \
    src/agg/include/agg_rendering_buffer.h \
    src/agg/include/agg_rendering_buffer_dynarow.h \
    src/agg/include/agg_rounded_rect.h \
    src/agg/include/agg_scanline_bin.h \
    src/agg/include/agg_scanline_boolean_algebra.h \
    src/agg/include/agg_scanline_p.h \
    src/agg/include/agg_scanline_storage_aa.h \
    src/agg/include/agg_scanline_storage_bin.h \
    src/agg/include/agg_scanline_u.h \
    src/agg/include/agg_shorten_path.h \
    src/agg/include/agg_simul_eq.h \
    src/agg/include/agg_span_allocator.h \
    src/agg/include/agg_span_converter.h \
    src/agg/include/agg_span_gouraud.h \
    src/agg/include/agg_span_gouraud_gray.h \
    src/agg/include/agg_span_gouraud_rgba.h \
    src/agg/include/agg_span_gradient.h \
    src/agg/include/agg_span_gradient_alpha.h \
    src/agg/include/agg_span_image_filter.h \
    src/agg/include/agg_span_image_filter_gray.h \
    src/agg/include/agg_span_image_filter_rgb.h \
    src/agg/include/agg_span_image_filter_rgba.h \
    src/agg/include/agg_span_interpolator_adaptor.h \
    src/agg/include/agg_span_interpolator_linear.h \
    src/agg/include/agg_span_interpolator_persp.h \
    src/agg/include/agg_span_interpolator_trans.h \
    src/agg/include/agg_span_pattern_gray.h \
    src/agg/include/agg_span_pattern_rgb.h \
    src/agg/include/agg_span_pattern_rgba.h \
    src/agg/include/agg_span_solid.h \
    src/agg/include/agg_span_subdiv_adaptor.h \
    src/agg/include/agg_trans_affine.h \
    src/agg/include/agg_trans_bilinear.h \
    src/agg/include/agg_trans_double_path.h \
    src/agg/include/agg_trans_perspective.h \
    src/agg/include/agg_trans_single_path.h \
    src/agg/include/agg_trans_viewport.h \
    src/agg/include/agg_trans_warp_magnifier.h \
    src/agg/include/agg_vcgen_bspline.h \
    src/agg/include/agg_vcgen_contour.h \
    src/agg/include/agg_vcgen_dash.h \
    src/agg/include/agg_vcgen_markers_term.h \
    src/agg/include/agg_vcgen_smooth_poly1.h \
    src/agg/include/agg_vcgen_stroke.h \
    src/agg/include/agg_vcgen_vertex_sequence.h \
    src/agg/include/agg_vertex_sequence.h \
    src/agg/include/agg_vpgen_clip_polygon.h \
    src/agg/include/agg_vpgen_clip_polyline.h \
    src/agg/include/agg_vpgen_segmentator.h \
    src/mainwindow.h

SOURCES += \
    src/agg/src/ctrl/agg_bezier_ctrl.cpp \
    src/agg/src/ctrl/agg_cbox_ctrl.cpp \
    src/agg/src/ctrl/agg_gamma_ctrl.cpp \
    src/agg/src/ctrl/agg_gamma_spline.cpp \
    src/agg/src/ctrl/agg_polygon_ctrl.cpp \
    src/agg/src/ctrl/agg_rbox_ctrl.cpp \
    src/agg/src/ctrl/agg_scale_ctrl.cpp \
    src/agg/src/ctrl/agg_slider_ctrl.cpp \
    src/agg/src/ctrl/agg_spline_ctrl.cpp \
    src/agg/src/agg_arc.cpp \
    src/agg/src/agg_arrowhead.cpp \
    src/agg/src/agg_bezier_arc.cpp \
    src/agg/src/agg_bspline.cpp \
    src/agg/src/agg_curves.cpp \
    src/agg/src/agg_embedded_raster_fonts.cpp \
    src/agg/src/agg_gsv_text.cpp \
    src/agg/src/agg_image_filters.cpp \
    src/agg/src/agg_line_aa_basics.cpp \
    src/agg/src/agg_line_profile_aa.cpp \
    src/agg/src/agg_rounded_rect.cpp \
    src/agg/src/agg_sqrt_tables.cpp \
    src/agg/src/agg_trans_affine.cpp \
    src/agg/src/agg_trans_double_path.cpp \
    src/agg/src/agg_trans_single_path.cpp \
    src/agg/src/agg_trans_warp_magnifier.cpp \
    src/agg/src/agg_vcgen_bspline.cpp \
    src/agg/src/agg_vcgen_contour.cpp \
    src/agg/src/agg_vcgen_dash.cpp \
    src/agg/src/agg_vcgen_markers_term.cpp \
    src/agg/src/agg_vcgen_smooth_poly1.cpp \
    src/agg/src/agg_vcgen_stroke.cpp \
    src/agg/src/agg_vpgen_clip_polygon.cpp \
    src/agg/src/agg_vpgen_clip_polyline.cpp \
    src/agg/src/agg_vpgen_segmentator.cpp \
    src/main.cpp \
    src/mainwindow.cpp

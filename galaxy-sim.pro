#-------------------------------------------------
#
# Project created by QtCreator 2016-02-05T02:55:22
#
#-------------------------------------------------

QT       += core gui

greaterThan(QT_MAJOR_VERSION, 4): QT += widgets

TARGET = galaxy-sim
TEMPLATE = app

INCLUDEPATH += agg/include


SOURCES += main.cpp\
        mainwindow.cpp \
    agg/src/ctrl/agg_bezier_ctrl.cpp \
    agg/src/ctrl/agg_cbox_ctrl.cpp \
    agg/src/ctrl/agg_gamma_ctrl.cpp \
    agg/src/ctrl/agg_gamma_spline.cpp \
    agg/src/ctrl/agg_polygon_ctrl.cpp \
    agg/src/ctrl/agg_rbox_ctrl.cpp \
    agg/src/ctrl/agg_scale_ctrl.cpp \
    agg/src/ctrl/agg_slider_ctrl.cpp \
    agg/src/ctrl/agg_spline_ctrl.cpp \
    agg/src/agg_arc.cpp \
    agg/src/agg_arrowhead.cpp \
    agg/src/agg_bezier_arc.cpp \
    agg/src/agg_bspline.cpp \
    agg/src/agg_curves.cpp \
    agg/src/agg_embedded_raster_fonts.cpp \
    agg/src/agg_gsv_text.cpp \
    agg/src/agg_image_filters.cpp \
    agg/src/agg_line_aa_basics.cpp \
    agg/src/agg_line_profile_aa.cpp \
    agg/src/agg_rounded_rect.cpp \
    agg/src/agg_sqrt_tables.cpp \
    agg/src/agg_trans_affine.cpp \
    agg/src/agg_trans_double_path.cpp \
    agg/src/agg_trans_single_path.cpp \
    agg/src/agg_trans_warp_magnifier.cpp \
    agg/src/agg_vcgen_bspline.cpp \
    agg/src/agg_vcgen_contour.cpp \
    agg/src/agg_vcgen_dash.cpp \
    agg/src/agg_vcgen_markers_term.cpp \
    agg/src/agg_vcgen_smooth_poly1.cpp \
    agg/src/agg_vcgen_stroke.cpp \
    agg/src/agg_vpgen_clip_polygon.cpp \
    agg/src/agg_vpgen_clip_polyline.cpp \
    agg/src/agg_vpgen_segmentator.cpp

HEADERS  += mainwindow.h \
    agg/include/ctrl/agg_bezier_ctrl.h \
    agg/include/ctrl/agg_cbox_ctrl.h \
    agg/include/ctrl/agg_ctrl.h \
    agg/include/ctrl/agg_gamma_ctrl.h \
    agg/include/ctrl/agg_gamma_spline.h \
    agg/include/ctrl/agg_polygon_ctrl.h \
    agg/include/ctrl/agg_rbox_ctrl.h \
    agg/include/ctrl/agg_scale_ctrl.h \
    agg/include/ctrl/agg_slider_ctrl.h \
    agg/include/ctrl/agg_spline_ctrl.h \
    agg/include/platform/mac/agg_mac_pmap.h \
    agg/include/platform/win32/agg_win32_bmp.h \
    agg/include/platform/agg_platform_support.h \
    agg/include/util/agg_color_conv.h \
    agg/include/util/agg_color_conv_rgb16.h \
    agg/include/util/agg_color_conv_rgb8.h \
    agg/include/agg_alpha_mask_u8.h \
    agg/include/agg_arc.h \
    agg/include/agg_array.h \
    agg/include/agg_arrowhead.h \
    agg/include/agg_basics.h \
    agg/include/agg_bezier_arc.h \
    agg/include/agg_bitset_iterator.h \
    agg/include/agg_blur.h \
    agg/include/agg_bounding_rect.h \
    agg/include/agg_bspline.h \
    agg/include/agg_clip_liang_barsky.h \
    agg/include/agg_color_gray.h \
    agg/include/agg_color_rgba.h \
    agg/include/agg_config.h \
    agg/include/agg_conv_adaptor_vcgen.h \
    agg/include/agg_conv_adaptor_vpgen.h \
    agg/include/agg_conv_bspline.h \
    agg/include/agg_conv_clip_polygon.h \
    agg/include/agg_conv_clip_polyline.h \
    agg/include/agg_conv_close_polygon.h \
    agg/include/agg_conv_concat.h \
    agg/include/agg_conv_contour.h \
    agg/include/agg_conv_curve.h \
    agg/include/agg_conv_dash.h \
    agg/include/agg_conv_gpc.h \
    agg/include/agg_conv_marker.h \
    agg/include/agg_conv_marker_adaptor.h \
    agg/include/agg_conv_segmentator.h \
    agg/include/agg_conv_shorten_path.h \
    agg/include/agg_conv_smooth_poly1.h \
    agg/include/agg_conv_stroke.h \
    agg/include/agg_conv_transform.h \
    agg/include/agg_conv_unclose_polygon.h \
    agg/include/agg_curves.h \
    agg/include/agg_dda_line.h \
    agg/include/agg_ellipse.h \
    agg/include/agg_ellipse_bresenham.h \
    agg/include/agg_embedded_raster_fonts.h \
    agg/include/agg_font_cache_manager.h \
    agg/include/agg_gamma_functions.h \
    agg/include/agg_gamma_lut.h \
    agg/include/agg_glyph_raster_bin.h \
    agg/include/agg_gradient_lut.h \
    agg/include/agg_gsv_text.h \
    agg/include/agg_image_accessors.h \
    agg/include/agg_image_filters.h \
    agg/include/agg_line_aa_basics.h \
    agg/include/agg_math.h \
    agg/include/agg_math_stroke.h \
    agg/include/agg_path_length.h \
    agg/include/agg_path_storage.h \
    agg/include/agg_path_storage_integer.h \
    agg/include/agg_pattern_filters_rgba.h \
    agg/include/agg_pixfmt_amask_adaptor.h \
    agg/include/agg_pixfmt_gray.h \
    agg/include/agg_pixfmt_rgb.h \
    agg/include/agg_pixfmt_rgb_packed.h \
    agg/include/agg_pixfmt_rgba.h \
    agg/include/agg_pixfmt_transposer.h \
    agg/include/agg_rasterizer_cells_aa.h \
    agg/include/agg_rasterizer_compound_aa.h \
    agg/include/agg_rasterizer_outline.h \
    agg/include/agg_rasterizer_outline_aa.h \
    agg/include/agg_rasterizer_scanline_aa.h \
    agg/include/agg_rasterizer_sl_clip.h \
    agg/include/agg_renderer_base.h \
    agg/include/agg_renderer_markers.h \
    agg/include/agg_renderer_mclip.h \
    agg/include/agg_renderer_outline_aa.h \
    agg/include/agg_renderer_outline_image.h \
    agg/include/agg_renderer_primitives.h \
    agg/include/agg_renderer_raster_text.h \
    agg/include/agg_renderer_scanline.h \
    agg/include/agg_rendering_buffer.h \
    agg/include/agg_rendering_buffer_dynarow.h \
    agg/include/agg_rounded_rect.h \
    agg/include/agg_scanline_bin.h \
    agg/include/agg_scanline_boolean_algebra.h \
    agg/include/agg_scanline_p.h \
    agg/include/agg_scanline_storage_aa.h \
    agg/include/agg_scanline_storage_bin.h \
    agg/include/agg_scanline_u.h \
    agg/include/agg_shorten_path.h \
    agg/include/agg_simul_eq.h \
    agg/include/agg_span_allocator.h \
    agg/include/agg_span_converter.h \
    agg/include/agg_span_gouraud.h \
    agg/include/agg_span_gouraud_gray.h \
    agg/include/agg_span_gouraud_rgba.h \
    agg/include/agg_span_gradient.h \
    agg/include/agg_span_gradient_alpha.h \
    agg/include/agg_span_image_filter.h \
    agg/include/agg_span_image_filter_gray.h \
    agg/include/agg_span_image_filter_rgb.h \
    agg/include/agg_span_image_filter_rgba.h \
    agg/include/agg_span_interpolator_adaptor.h \
    agg/include/agg_span_interpolator_linear.h \
    agg/include/agg_span_interpolator_persp.h \
    agg/include/agg_span_interpolator_trans.h \
    agg/include/agg_span_pattern_gray.h \
    agg/include/agg_span_pattern_rgb.h \
    agg/include/agg_span_pattern_rgba.h \
    agg/include/agg_span_solid.h \
    agg/include/agg_span_subdiv_adaptor.h \
    agg/include/agg_trans_affine.h \
    agg/include/agg_trans_bilinear.h \
    agg/include/agg_trans_double_path.h \
    agg/include/agg_trans_perspective.h \
    agg/include/agg_trans_single_path.h \
    agg/include/agg_trans_viewport.h \
    agg/include/agg_trans_warp_magnifier.h \
    agg/include/agg_vcgen_bspline.h \
    agg/include/agg_vcgen_contour.h \
    agg/include/agg_vcgen_dash.h \
    agg/include/agg_vcgen_markers_term.h \
    agg/include/agg_vcgen_smooth_poly1.h \
    agg/include/agg_vcgen_stroke.h \
    agg/include/agg_vcgen_vertex_sequence.h \
    agg/include/agg_vertex_sequence.h \
    agg/include/agg_vpgen_clip_polygon.h \
    agg/include/agg_vpgen_clip_polyline.h \
    agg/include/agg_vpgen_segmentator.h

FORMS    += mainwindow.ui

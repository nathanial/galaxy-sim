#-------------------------------------------------
#
# Project created by QtCreator 2016-02-05T02:55:22
#
#-------------------------------------------------

QT       += core gui

greaterThan(QT_MAJOR_VERSION, 4): QT += widgets

TARGET = galaxy-sim
TEMPLATE = app

QMAKE_CXX = clang

CONFIG += c++14

INCLUDEPATH += src/galaxy\
  src/noise\
  vendor/skia/include/core\
  vendor/skia/include/config\
  vendor/skia/include/effects


FORMS    +=

DISTFILES += \
    TODO.txt \
    resources/StarColorGradient.png \
    resources/SpaceNebulaTexture.jpg

HEADERS += \
    src/mainwindow.h \
    src/galaxy/galaxy.hpp \
    vendor/skia/include/android/SkBitmapRegionDecoder.h \
    vendor/skia/include/android/SkBRDAllocator.h \
    vendor/skia/include/animator/SkAnimator.h \
    vendor/skia/include/animator/SkAnimatorView.h \
    vendor/skia/include/c/sk_canvas.h \
    vendor/skia/include/c/sk_data.h \
    vendor/skia/include/c/sk_image.h \
    vendor/skia/include/c/sk_maskfilter.h \
    vendor/skia/include/c/sk_matrix.h \
    vendor/skia/include/c/sk_paint.h \
    vendor/skia/include/c/sk_path.h \
    vendor/skia/include/c/sk_picture.h \
    vendor/skia/include/c/sk_shader.h \
    vendor/skia/include/c/sk_surface.h \
    vendor/skia/include/c/sk_types.h \
    vendor/skia/include/client/android/SkAvoidXfermode.h \
    vendor/skia/include/client/android/SkPixelXorXfermode.h \
    vendor/skia/include/codec/SkAndroidCodec.h \
    vendor/skia/include/codec/SkCodec.h \
    vendor/skia/include/codec/SkEncodedFormat.h \
    vendor/skia/include/config/SkUserConfig.h \
    vendor/skia/include/core/SkAnnotation.h \
    vendor/skia/include/core/SkBBHFactory.h \
    vendor/skia/include/core/SkBitmap.h \
    vendor/skia/include/core/SkBitmapDevice.h \
    vendor/skia/include/core/SkBlitRow.h \
    vendor/skia/include/core/SkBlurTypes.h \
    vendor/skia/include/core/SkCanvas.h \
    vendor/skia/include/core/SkChunkAlloc.h \
    vendor/skia/include/core/SkClipStack.h \
    vendor/skia/include/core/SkColor.h \
    vendor/skia/include/core/SkColorFilter.h \
    vendor/skia/include/core/SkColorPriv.h \
    vendor/skia/include/core/SkColorTable.h \
    vendor/skia/include/core/SkComposeShader.h \
    vendor/skia/include/core/SkData.h \
    vendor/skia/include/core/SkDataTable.h \
    vendor/skia/include/core/SkDeque.h \
    vendor/skia/include/core/SkDevice.h \
    vendor/skia/include/core/SkDocument.h \
    vendor/skia/include/core/SkDraw.h \
    vendor/skia/include/core/SkDrawable.h \
    vendor/skia/include/core/SkDrawFilter.h \
    vendor/skia/include/core/SkDrawLooper.h \
    vendor/skia/include/core/SkError.h \
    vendor/skia/include/core/SkFilterQuality.h \
    vendor/skia/include/core/SkFixed.h \
    vendor/skia/include/core/SkFlattenable.h \
    vendor/skia/include/core/SkFlattenableSerialization.h \
    vendor/skia/include/core/SkFont.h \
    vendor/skia/include/core/SkFontHost.h \
    vendor/skia/include/core/SkFontLCDConfig.h \
    vendor/skia/include/core/SkFontStyle.h \
    vendor/skia/include/core/SkGraphics.h \
    vendor/skia/include/core/SkImage.h \
    vendor/skia/include/core/SkImageDecoder.h \
    vendor/skia/include/core/SkImageEncoder.h \
    vendor/skia/include/core/SkImageFilter.h \
    vendor/skia/include/core/SkImageGenerator.h \
    vendor/skia/include/core/SkImageInfo.h \
    vendor/skia/include/core/SkMallocPixelRef.h \
    vendor/skia/include/core/SkMask.h \
    vendor/skia/include/core/SkMaskFilter.h \
    vendor/skia/include/core/SkMath.h \
    vendor/skia/include/core/SkMatrix.h \
    vendor/skia/include/core/SkMetaData.h \
    vendor/skia/include/core/SkMultiPictureDraw.h \
    vendor/skia/include/core/SkOSFile.h \
    vendor/skia/include/core/SkPackBits.h \
    vendor/skia/include/core/SkPaint.h \
    vendor/skia/include/core/SkPath.h \
    vendor/skia/include/core/SkPathEffect.h \
    vendor/skia/include/core/SkPathMeasure.h \
    vendor/skia/include/core/SkPathRef.h \
    vendor/skia/include/core/SkPicture.h \
    vendor/skia/include/core/SkPictureRecorder.h \
    vendor/skia/include/core/SkPixelRef.h \
    vendor/skia/include/core/SkPixelSerializer.h \
    vendor/skia/include/core/SkPixmap.h \
    vendor/skia/include/core/SkPngChunkReader.h \
    vendor/skia/include/core/SkPoint.h \
    vendor/skia/include/core/SkPoint3.h \
    vendor/skia/include/core/SkPostConfig.h \
    vendor/skia/include/core/SkPreConfig.h \
    vendor/skia/include/core/SkRasterizer.h \
    vendor/skia/include/core/SkRect.h \
    vendor/skia/include/core/SkRefCnt.h \
    vendor/skia/include/core/SkRegion.h \
    vendor/skia/include/core/SkRRect.h \
    vendor/skia/include/core/SkRSXform.h \
    vendor/skia/include/core/SkScalar.h \
    vendor/skia/include/core/SkShader.h \
    vendor/skia/include/core/SkSize.h \
    vendor/skia/include/core/SkStream.h \
    vendor/skia/include/core/SkString.h \
    vendor/skia/include/core/SkStrokeRec.h \
    vendor/skia/include/core/SkSurface.h \
    vendor/skia/include/core/SkSurfaceProps.h \
    vendor/skia/include/core/SkTArray.h \
    vendor/skia/include/core/SkTDArray.h \
    vendor/skia/include/core/SkTDStack.h \
    vendor/skia/include/core/SkTextBlob.h \
    vendor/skia/include/core/SkTime.h \
    vendor/skia/include/core/SkTInternalLList.h \
    vendor/skia/include/core/SkTLazy.h \
    vendor/skia/include/core/SkTraceMemoryDump.h \
    vendor/skia/include/core/SkTRegistry.h \
    vendor/skia/include/core/SkTypeface.h \
    vendor/skia/include/core/SkTypes.h \
    vendor/skia/include/core/SkUnPreMultiply.h \
    vendor/skia/include/core/SkUtils.h \
    vendor/skia/include/core/SkWriteBuffer.h \
    vendor/skia/include/core/SkWriter32.h \
    vendor/skia/include/core/SkXfermode.h \
    vendor/skia/include/device/xps/SkConstexprMath.h \
    vendor/skia/include/device/xps/SkXPSDevice.h \
    vendor/skia/include/effects/Sk1DPathEffect.h \
    vendor/skia/include/effects/Sk2DPathEffect.h \
    vendor/skia/include/effects/SkAlphaThresholdFilter.h \
    vendor/skia/include/effects/SkArcToPathEffect.h \
    vendor/skia/include/effects/SkArithmeticMode.h \
    vendor/skia/include/effects/SkBlurDrawLooper.h \
    vendor/skia/include/effects/SkBlurImageFilter.h \
    vendor/skia/include/effects/SkBlurMaskFilter.h \
    vendor/skia/include/effects/SkColorCubeFilter.h \
    vendor/skia/include/effects/SkColorFilterImageFilter.h \
    vendor/skia/include/effects/SkColorMatrix.h \
    vendor/skia/include/effects/SkColorMatrixFilter.h \
    vendor/skia/include/effects/SkComposeImageFilter.h \
    vendor/skia/include/effects/SkCornerPathEffect.h \
    vendor/skia/include/effects/SkDashPathEffect.h \
    vendor/skia/include/effects/SkDiscretePathEffect.h \
    vendor/skia/include/effects/SkDisplacementMapEffect.h \
    vendor/skia/include/effects/SkDrawExtraPathEffect.h \
    vendor/skia/include/effects/SkDropShadowImageFilter.h \
    vendor/skia/include/effects/SkEmbossMaskFilter.h \
    vendor/skia/include/effects/SkGradientShader.h \
    vendor/skia/include/effects/SkImageSource.h \
    vendor/skia/include/effects/SkLayerDrawLooper.h \
    vendor/skia/include/effects/SkLayerRasterizer.h \
    vendor/skia/include/effects/SkLightingImageFilter.h \
    vendor/skia/include/effects/SkLumaColorFilter.h \
    vendor/skia/include/effects/SkMagnifierImageFilter.h \
    vendor/skia/include/effects/SkMatrixConvolutionImageFilter.h \
    vendor/skia/include/effects/SkMergeImageFilter.h \
    vendor/skia/include/effects/SkMorphologyImageFilter.h \
    vendor/skia/include/effects/SkOffsetImageFilter.h \
    vendor/skia/include/effects/SkPaintFlagsDrawFilter.h \
    vendor/skia/include/effects/SkPaintImageFilter.h \
    vendor/skia/include/effects/SkPerlinNoiseShader.h \
    vendor/skia/include/effects/SkPictureImageFilter.h \
    vendor/skia/include/effects/SkTableColorFilter.h \
    vendor/skia/include/effects/SkTableMaskFilter.h \
    vendor/skia/include/effects/SkTestImageFilters.h \
    vendor/skia/include/effects/SkTileImageFilter.h \
    vendor/skia/include/effects/SkXfermodeImageFilter.h \
    vendor/skia/include/gpu/effects/GrConstColorProcessor.h \
    vendor/skia/include/gpu/effects/GrCoverageSetOpXP.h \
    vendor/skia/include/gpu/effects/GrCustomXfermode.h \
    vendor/skia/include/gpu/effects/GrPorterDuffXferProcessor.h \
    vendor/skia/include/gpu/effects/GrXfermodeFragmentProcessor.h \
    vendor/skia/include/gpu/gl/angle/SkANGLEGLContext.h \
    vendor/skia/include/gpu/gl/command_buffer/SkCommandBufferGLContext.h \
    vendor/skia/include/gpu/gl/GrGLConfig.h \
    vendor/skia/include/gpu/gl/GrGLConfig_chrome.h \
    vendor/skia/include/gpu/gl/GrGLExtensions.h \
    vendor/skia/include/gpu/gl/GrGLFunctions.h \
    vendor/skia/include/gpu/gl/GrGLInterface.h \
    vendor/skia/include/gpu/gl/GrGLSLPrettyPrint.h \
    vendor/skia/include/gpu/gl/GrGLTypes.h \
    vendor/skia/include/gpu/gl/SkGLContext.h \
    vendor/skia/include/gpu/gl/SkNullGLContext.h \
    vendor/skia/include/gpu/GrBlend.h \
    vendor/skia/include/gpu/GrCaps.h \
    vendor/skia/include/gpu/GrClip.h \
    vendor/skia/include/gpu/GrColor.h \
    vendor/skia/include/gpu/GrConfig.h \
    vendor/skia/include/gpu/GrContext.h \
    vendor/skia/include/gpu/GrContextOptions.h \
    vendor/skia/include/gpu/GrCoordTransform.h \
    vendor/skia/include/gpu/GrDrawContext.h \
    vendor/skia/include/gpu/GrFragmentProcessor.h \
    vendor/skia/include/gpu/GrGpuResource.h \
    vendor/skia/include/gpu/GrGpuResourceRef.h \
    vendor/skia/include/gpu/GrInvariantOutput.h \
    vendor/skia/include/gpu/GrPaint.h \
    vendor/skia/include/gpu/GrProcessor.h \
    vendor/skia/include/gpu/GrProcessorUnitTest.h \
    vendor/skia/include/gpu/GrProgramElement.h \
    vendor/skia/include/gpu/GrRenderTarget.h \
    vendor/skia/include/gpu/GrResourceKey.h \
    vendor/skia/include/gpu/GrShaderVar.h \
    vendor/skia/include/gpu/GrSurface.h \
    vendor/skia/include/gpu/GrTestUtils.h \
    vendor/skia/include/gpu/GrTexture.h \
    vendor/skia/include/gpu/GrTextureAccess.h \
    vendor/skia/include/gpu/GrTextureParams.h \
    vendor/skia/include/gpu/GrTextureProvider.h \
    vendor/skia/include/gpu/GrTypes.h \
    vendor/skia/include/gpu/GrTypesPriv.h \
    vendor/skia/include/gpu/GrXferProcessor.h \
    vendor/skia/include/gpu/SkGr.h \
    vendor/skia/include/gpu/SkGrPixelRef.h \
    vendor/skia/include/gpu/SkGrTexturePixelRef.h \
    vendor/skia/include/images/SkDecodingImageGenerator.h \
    vendor/skia/include/images/SkForceLinking.h \
    vendor/skia/include/images/SkMovie.h \
    vendor/skia/include/images/SkPageFlipper.h \
    vendor/skia/include/pathops/SkPathOps.h \
    vendor/skia/include/ports/SkFontConfigInterface.h \
    vendor/skia/include/ports/SkFontMgr.h \
    vendor/skia/include/ports/SkFontMgr_android.h \
    vendor/skia/include/ports/SkFontMgr_custom.h \
    vendor/skia/include/ports/SkFontMgr_fontconfig.h \
    vendor/skia/include/ports/SkFontMgr_indirect.h \
    vendor/skia/include/ports/SkRemotableFontMgr.h \
    vendor/skia/include/ports/SkTypeface_mac.h \
    vendor/skia/include/ports/SkTypeface_win.h \
    vendor/skia/include/private/GrAuditTrail.h \
    vendor/skia/include/private/GrSingleOwner.h \
    vendor/skia/include/private/SkAtomics.h \
    vendor/skia/include/private/SkChecksum.h \
    vendor/skia/include/private/SkFloatBits.h \
    vendor/skia/include/private/SkFloatingPoint.h \
    vendor/skia/include/private/SkGpuFenceSync.h \
    vendor/skia/include/private/SkMiniRecorder.h \
    vendor/skia/include/private/SkMutex.h \
    vendor/skia/include/private/SkOnce.h \
    vendor/skia/include/private/SkOncePtr.h \
    vendor/skia/include/private/SkRecords.h \
    vendor/skia/include/private/SkSemaphore.h \
    vendor/skia/include/private/SkSpinlock.h \
    vendor/skia/include/private/SkTDict.h \
    vendor/skia/include/private/SkTemplates.h \
    vendor/skia/include/private/SkTHash.h \
    vendor/skia/include/private/SkThreadID.h \
    vendor/skia/include/private/SkTLogic.h \
    vendor/skia/include/private/SkTSearch.h \
    vendor/skia/include/private/SkUniquePtr.h \
    vendor/skia/include/private/SkWeakRefCnt.h \
    vendor/skia/include/svg/parser/SkSVGAttribute.h \
    vendor/skia/include/svg/parser/SkSVGBase.h \
    vendor/skia/include/svg/parser/SkSVGPaintState.h \
    vendor/skia/include/svg/parser/SkSVGParser.h \
    vendor/skia/include/svg/parser/SkSVGTypes.h \
    vendor/skia/include/svg/SkSVGCanvas.h \
    vendor/skia/include/utils/mac/SkCGUtils.h \
    vendor/skia/include/utils/win/SkAutoCoInitialize.h \
    vendor/skia/include/utils/win/SkHRESULT.h \
    vendor/skia/include/utils/win/SkIStream.h \
    vendor/skia/include/utils/win/SkTScopedComPtr.h \
    vendor/skia/include/utils/SkBoundaryPatch.h \
    vendor/skia/include/utils/SkCamera.h \
    vendor/skia/include/utils/SkCanvasStateUtils.h \
    vendor/skia/include/utils/SkDumpCanvas.h \
    vendor/skia/include/utils/SkEventTracer.h \
    vendor/skia/include/utils/SkFrontBufferedStream.h \
    vendor/skia/include/utils/SkInterpolator.h \
    vendor/skia/include/utils/SkJSONCPP.h \
    vendor/skia/include/utils/SkLayer.h \
    vendor/skia/include/utils/SkLua.h \
    vendor/skia/include/utils/SkLuaCanvas.h \
    vendor/skia/include/utils/SkMatrix44.h \
    vendor/skia/include/utils/SkMeshUtils.h \
    vendor/skia/include/utils/SkNinePatch.h \
    vendor/skia/include/utils/SkNoSaveLayerCanvas.h \
    vendor/skia/include/utils/SkNullCanvas.h \
    vendor/skia/include/utils/SkNWayCanvas.h \
    vendor/skia/include/utils/SkPaintFilterCanvas.h \
    vendor/skia/include/utils/SkParse.h \
    vendor/skia/include/utils/SkParsePath.h \
    vendor/skia/include/utils/SkPictureUtils.h \
    vendor/skia/include/utils/SkRandom.h \
    vendor/skia/include/utils/SkRTConf.h \
    vendor/skia/include/utils/SkTextBox.h \
    vendor/skia/include/views/animated/SkBorderView.h \
    vendor/skia/include/views/animated/SkImageView.h \
    vendor/skia/include/views/animated/SkProgressBarView.h \
    vendor/skia/include/views/animated/SkScrollBarView.h \
    vendor/skia/include/views/animated/SkWidgetViews.h \
    vendor/skia/include/views/SkApplication.h \
    vendor/skia/include/views/SkBGViewArtist.h \
    vendor/skia/include/views/SkEvent.h \
    vendor/skia/include/views/SkEventSink.h \
    vendor/skia/include/views/SkKey.h \
    vendor/skia/include/views/SkOSMenu.h \
    vendor/skia/include/views/SkOSWindow_Android.h \
    vendor/skia/include/views/SkOSWindow_iOS.h \
    vendor/skia/include/views/SkOSWindow_Mac.h \
    vendor/skia/include/views/SkOSWindow_SDL.h \
    vendor/skia/include/views/SkOSWindow_Unix.h \
    vendor/skia/include/views/SkOSWindow_Win.h \
    vendor/skia/include/views/SkParsePaint.h \
    vendor/skia/include/views/SkStackViewLayout.h \
    vendor/skia/include/views/SkSystemEventTypes.h \
    vendor/skia/include/views/SkTouchGesture.h \
    vendor/skia/include/views/SkView.h \
    vendor/skia/include/views/SkViewInflate.h \
    vendor/skia/include/views/SkWidget.h \
    vendor/skia/include/views/SkWindow.h \
    vendor/skia/include/xml/SkBML_WXMLParser.h \
    vendor/skia/include/xml/SkBML_XMLParser.h \
    vendor/skia/include/xml/SkDOM.h \
    vendor/skia/include/xml/SkXMLParser.h \
    vendor/skia/include/xml/SkXMLWriter.h \
    src/galaxy/galaxyview.h \
    src/noise/PerlinNoise.h

SOURCES += \
    src/main.cpp \
    src/mainwindow.cpp \
    src/galaxy/galaxy.cpp \
    src/galaxy/galaxyview.cpp \
    src/noise/PerlinNoise.cpp


#PRE_TARGETDEPS += $$PWD/vendor/skia/out/Debug/libskia_core.a

LIBS += -L$$PWD/vendor/skia/out/Release/lib/ -lskia

INCLUDEPATH += $$PWD/vendor/skia/out/Release
DEPENDPATH += $$PWD/vendor/skia/out/Release

RESOURCES += \
    images.qrc

/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (https://www.swig.org).
 * Version 4.1.1
 *
 * Do not make changes to this file unless you know what you are doing - modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */


public class lightmapperJNI {
  public final static native int LM_FALSE_get();
  public final static native int LM_TRUE_get();
  public final static native int LM_NONE_get();
  public final static native long lmCreate__SWIG_0(int jarg1, float jarg2, float jarg3, float jarg4, float jarg5, float jarg6, int jarg7, float jarg8, float jarg9);
  public final static native long lmCreate__SWIG_1(int jarg1, float jarg2, float jarg3, float jarg4, float jarg5, float jarg6, int jarg7, float jarg8);
  public final static native void lmSetHemisphereWeights(long jarg1, long jarg2, long jarg3);
  public final static native void lmSetTargetLightmap(long jarg1, long jarg2, int jarg3, int jarg4, int jarg5);
  public final static native void lmSetGeometry__SWIG_0(long jarg1, long jarg2, int jarg3, long jarg4, int jarg5, int jarg6, long jarg7, int jarg8, int jarg9, long jarg10, int jarg11, int jarg12, int jarg13, long jarg14);
  public final static native void lmSetGeometry__SWIG_1(long jarg1, long jarg2, int jarg3, long jarg4, int jarg5, int jarg6, long jarg7, int jarg8, int jarg9, long jarg10, int jarg11, int jarg12, int jarg13);
  public final static native void lmSetGeometry__SWIG_2(long jarg1, long jarg2, int jarg3, long jarg4, int jarg5, int jarg6, long jarg7, int jarg8, int jarg9, long jarg10, int jarg11, int jarg12);
  public final static native int lmBegin(long jarg1, long jarg2, long jarg3, long jarg4);
  public final static native float lmProgress(long jarg1);
  public final static native void lmEnd(long jarg1);
  public final static native void lmDestroy(long jarg1);
  public final static native int LM_ALL_CHANNELS_get();
  public final static native float lmImageMin__SWIG_0(long jarg1, int jarg2, int jarg3, int jarg4, int jarg5);
  public final static native float lmImageMin__SWIG_1(long jarg1, int jarg2, int jarg3, int jarg4);
  public final static native float lmImageMax__SWIG_0(long jarg1, int jarg2, int jarg3, int jarg4, int jarg5);
  public final static native float lmImageMax__SWIG_1(long jarg1, int jarg2, int jarg3, int jarg4);
  public final static native void lmImageAdd__SWIG_0(long jarg1, int jarg2, int jarg3, int jarg4, float jarg5, int jarg6);
  public final static native void lmImageAdd__SWIG_1(long jarg1, int jarg2, int jarg3, int jarg4, float jarg5);
  public final static native void lmImageScale__SWIG_0(long jarg1, int jarg2, int jarg3, int jarg4, float jarg5, int jarg6);
  public final static native void lmImageScale__SWIG_1(long jarg1, int jarg2, int jarg3, int jarg4, float jarg5);
  public final static native void lmImagePower__SWIG_0(long jarg1, int jarg2, int jarg3, int jarg4, float jarg5, int jarg6);
  public final static native void lmImagePower__SWIG_1(long jarg1, int jarg2, int jarg3, int jarg4, float jarg5);
  public final static native void lmImageDilate(long jarg1, long jarg2, int jarg3, int jarg4, int jarg5);
  public final static native void lmImageSmooth(long jarg1, long jarg2, int jarg3, int jarg4, int jarg5);
  public final static native void lmImageDownsample(long jarg1, long jarg2, int jarg3, int jarg4, int jarg5);
  public final static native void lmImageFtoUB__SWIG_0(long jarg1, long jarg2, int jarg3, int jarg4, int jarg5, float jarg6);
  public final static native void lmImageFtoUB__SWIG_1(long jarg1, long jarg2, int jarg3, int jarg4, int jarg5);
  public final static native int lmImageSaveTGAub(String jarg1, long jarg2, int jarg3, int jarg4, int jarg5);
  public final static native int lmImageSaveTGAf__SWIG_0(String jarg1, long jarg2, int jarg3, int jarg4, int jarg5, float jarg6);
  public final static native int lmImageSaveTGAf__SWIG_1(String jarg1, long jarg2, int jarg3, int jarg4, int jarg5);
}

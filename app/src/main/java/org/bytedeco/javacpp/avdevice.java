package org.bytedeco.javacpp;

import org.bytedeco.javacpp.annotation.ByPtrPtr;
import org.bytedeco.javacpp.annotation.ByRef;
import org.bytedeco.javacpp.annotation.Cast;
import org.bytedeco.javacpp.annotation.Const;
import org.bytedeco.javacpp.annotation.MemberGetter;
import org.bytedeco.javacpp.avformat.AVFormatContext;
import org.bytedeco.javacpp.avformat.AVInputFormat;
import org.bytedeco.javacpp.avformat.AVOutputFormat;
import org.bytedeco.javacpp.avutil.AVClass;
import org.bytedeco.javacpp.avutil.AVDictionary;
import org.bytedeco.javacpp.avutil.AVRational;

public class avdevice extends org.bytedeco.javacpp.presets.avdevice {
    public static final int AV_APP_TO_DEV_GET_MUTE = AV_APP_TO_DEV_GET_MUTE();
    public static final int AV_APP_TO_DEV_GET_VOLUME = AV_APP_TO_DEV_GET_VOLUME();
    public static final int AV_APP_TO_DEV_MUTE = AV_APP_TO_DEV_MUTE();
    public static final int AV_APP_TO_DEV_NONE = AV_APP_TO_DEV_NONE();
    public static final int AV_APP_TO_DEV_PAUSE = AV_APP_TO_DEV_PAUSE();
    public static final int AV_APP_TO_DEV_PLAY = AV_APP_TO_DEV_PLAY();
    public static final int AV_APP_TO_DEV_SET_VOLUME = AV_APP_TO_DEV_SET_VOLUME();
    public static final int AV_APP_TO_DEV_TOGGLE_MUTE = AV_APP_TO_DEV_TOGGLE_MUTE();
    public static final int AV_APP_TO_DEV_TOGGLE_PAUSE = AV_APP_TO_DEV_TOGGLE_PAUSE();
    public static final int AV_APP_TO_DEV_UNMUTE = AV_APP_TO_DEV_UNMUTE();
    public static final int AV_APP_TO_DEV_WINDOW_REPAINT = AV_APP_TO_DEV_WINDOW_REPAINT();
    public static final int AV_APP_TO_DEV_WINDOW_SIZE = AV_APP_TO_DEV_WINDOW_SIZE();
    public static final int AV_DEV_TO_APP_BUFFER_OVERFLOW = AV_DEV_TO_APP_BUFFER_OVERFLOW();
    public static final int AV_DEV_TO_APP_BUFFER_READABLE = AV_DEV_TO_APP_BUFFER_READABLE();
    public static final int AV_DEV_TO_APP_BUFFER_UNDERFLOW = AV_DEV_TO_APP_BUFFER_UNDERFLOW();
    public static final int AV_DEV_TO_APP_BUFFER_WRITABLE = AV_DEV_TO_APP_BUFFER_WRITABLE();
    public static final int AV_DEV_TO_APP_CREATE_WINDOW_BUFFER = AV_DEV_TO_APP_CREATE_WINDOW_BUFFER();
    public static final int AV_DEV_TO_APP_DESTROY_WINDOW_BUFFER = AV_DEV_TO_APP_DESTROY_WINDOW_BUFFER();
    public static final int AV_DEV_TO_APP_DISPLAY_WINDOW_BUFFER = AV_DEV_TO_APP_DISPLAY_WINDOW_BUFFER();
    public static final int AV_DEV_TO_APP_MUTE_STATE_CHANGED = AV_DEV_TO_APP_MUTE_STATE_CHANGED();
    public static final int AV_DEV_TO_APP_NONE = AV_DEV_TO_APP_NONE();
    public static final int AV_DEV_TO_APP_PREPARE_WINDOW_BUFFER = AV_DEV_TO_APP_PREPARE_WINDOW_BUFFER();
    public static final int AV_DEV_TO_APP_VOLUME_LEVEL_CHANGED = AV_DEV_TO_APP_VOLUME_LEVEL_CHANGED();

    public static class AVDeviceCapabilitiesQuery extends Pointer {
        private native void allocate();

        private native void allocateArray(int i);

        @MemberGetter
        @Const
        public native AVClass av_class();

        public native long channel_layout();

        public native AVDeviceCapabilitiesQuery channel_layout(long j);

        public native int channels();

        public native AVDeviceCapabilitiesQuery channels(int i);

        @Cast({"AVCodecID"})
        public native int codec();

        public native AVDeviceCapabilitiesQuery codec(int i);

        public native AVDeviceCapabilitiesQuery device_context(AVFormatContext aVFormatContext);

        public native AVFormatContext device_context();

        public native AVDeviceCapabilitiesQuery fps(AVRational aVRational);

        @ByRef
        public native AVRational fps();

        public native int frame_height();

        public native AVDeviceCapabilitiesQuery frame_height(int i);

        public native int frame_width();

        public native AVDeviceCapabilitiesQuery frame_width(int i);

        @Cast({"AVPixelFormat"})
        public native int pixel_format();

        public native AVDeviceCapabilitiesQuery pixel_format(int i);

        @Cast({"AVSampleFormat"})
        public native int sample_format();

        public native AVDeviceCapabilitiesQuery sample_format(int i);

        public native int sample_rate();

        public native AVDeviceCapabilitiesQuery sample_rate(int i);

        public native int window_height();

        public native AVDeviceCapabilitiesQuery window_height(int i);

        public native int window_width();

        public native AVDeviceCapabilitiesQuery window_width(int i);

        static {
            Loader.load();
        }

        public AVDeviceCapabilitiesQuery() {
            allocate();
        }

        public AVDeviceCapabilitiesQuery(int size) {
            allocateArray(size);
        }

        public AVDeviceCapabilitiesQuery(Pointer p) {
            super(p);
        }

        public AVDeviceCapabilitiesQuery position(int position) {
            return (AVDeviceCapabilitiesQuery) super.position(position);
        }
    }

    public static class AVDeviceInfo extends Pointer {
        private native void allocate();

        private native void allocateArray(int i);

        @Cast({"char*"})
        public native BytePointer device_description();

        public native AVDeviceInfo device_description(BytePointer bytePointer);

        @Cast({"char*"})
        public native BytePointer device_name();

        public native AVDeviceInfo device_name(BytePointer bytePointer);

        static {
            Loader.load();
        }

        public AVDeviceInfo() {
            allocate();
        }

        public AVDeviceInfo(int size) {
            allocateArray(size);
        }

        public AVDeviceInfo(Pointer p) {
            super(p);
        }

        public AVDeviceInfo position(int position) {
            return (AVDeviceInfo) super.position(position);
        }
    }

    public static class AVDeviceInfoList extends Pointer {
        private native void allocate();

        private native void allocateArray(int i);

        public native int default_device();

        public native AVDeviceInfoList default_device(int i);

        @MemberGetter
        @Cast({"AVDeviceInfo**"})
        public native PointerPointer devices();

        public native AVDeviceInfo devices(int i);

        public native AVDeviceInfoList devices(int i, AVDeviceInfo aVDeviceInfo);

        public native int nb_devices();

        public native AVDeviceInfoList nb_devices(int i);

        static {
            Loader.load();
        }

        public AVDeviceInfoList() {
            allocate();
        }

        public AVDeviceInfoList(int size) {
            allocateArray(size);
        }

        public AVDeviceInfoList(Pointer p) {
            super(p);
        }

        public AVDeviceInfoList position(int position) {
            return (AVDeviceInfoList) super.position(position);
        }
    }

    @MemberGetter
    public static native int AV_APP_TO_DEV_GET_MUTE();

    @MemberGetter
    public static native int AV_APP_TO_DEV_GET_VOLUME();

    @MemberGetter
    public static native int AV_APP_TO_DEV_MUTE();

    @MemberGetter
    public static native int AV_APP_TO_DEV_NONE();

    @MemberGetter
    public static native int AV_APP_TO_DEV_PAUSE();

    @MemberGetter
    public static native int AV_APP_TO_DEV_PLAY();

    @MemberGetter
    public static native int AV_APP_TO_DEV_SET_VOLUME();

    @MemberGetter
    public static native int AV_APP_TO_DEV_TOGGLE_MUTE();

    @MemberGetter
    public static native int AV_APP_TO_DEV_TOGGLE_PAUSE();

    @MemberGetter
    public static native int AV_APP_TO_DEV_UNMUTE();

    @MemberGetter
    public static native int AV_APP_TO_DEV_WINDOW_REPAINT();

    @MemberGetter
    public static native int AV_APP_TO_DEV_WINDOW_SIZE();

    @MemberGetter
    public static native int AV_DEV_TO_APP_BUFFER_OVERFLOW();

    @MemberGetter
    public static native int AV_DEV_TO_APP_BUFFER_READABLE();

    @MemberGetter
    public static native int AV_DEV_TO_APP_BUFFER_UNDERFLOW();

    @MemberGetter
    public static native int AV_DEV_TO_APP_BUFFER_WRITABLE();

    @MemberGetter
    public static native int AV_DEV_TO_APP_CREATE_WINDOW_BUFFER();

    @MemberGetter
    public static native int AV_DEV_TO_APP_DESTROY_WINDOW_BUFFER();

    @MemberGetter
    public static native int AV_DEV_TO_APP_DISPLAY_WINDOW_BUFFER();

    @MemberGetter
    public static native int AV_DEV_TO_APP_MUTE_STATE_CHANGED();

    @MemberGetter
    public static native int AV_DEV_TO_APP_NONE();

    @MemberGetter
    public static native int AV_DEV_TO_APP_PREPARE_WINDOW_BUFFER();

    @MemberGetter
    public static native int AV_DEV_TO_APP_VOLUME_LEVEL_CHANGED();

    public static native AVInputFormat av_input_audio_device_next(AVInputFormat aVInputFormat);

    public static native AVInputFormat av_input_video_device_next(AVInputFormat aVInputFormat);

    public static native AVOutputFormat av_output_audio_device_next(AVOutputFormat aVOutputFormat);

    public static native AVOutputFormat av_output_video_device_next(AVOutputFormat aVOutputFormat);

    public static native int avdevice_app_to_dev_control_message(AVFormatContext aVFormatContext, @Cast({"AVAppToDevMessageType"}) int i, Pointer pointer, @Cast({"size_t"}) long j);

    public static native int avdevice_capabilities_create(@Cast({"AVDeviceCapabilitiesQuery**"}) PointerPointer pointerPointer, AVFormatContext aVFormatContext, @Cast({"AVDictionary**"}) PointerPointer pointerPointer2);

    public static native int avdevice_capabilities_create(@ByPtrPtr AVDeviceCapabilitiesQuery aVDeviceCapabilitiesQuery, AVFormatContext aVFormatContext, @ByPtrPtr AVDictionary aVDictionary);

    public static native void avdevice_capabilities_free(@Cast({"AVDeviceCapabilitiesQuery**"}) PointerPointer pointerPointer, AVFormatContext aVFormatContext);

    public static native void avdevice_capabilities_free(@ByPtrPtr AVDeviceCapabilitiesQuery aVDeviceCapabilitiesQuery, AVFormatContext aVFormatContext);

    @Cast({"const char*"})
    public static native BytePointer avdevice_configuration();

    public static native int avdevice_dev_to_app_control_message(AVFormatContext aVFormatContext, @Cast({"AVDevToAppMessageType"}) int i, Pointer pointer, @Cast({"size_t"}) long j);

    public static native void avdevice_free_list_devices(@Cast({"AVDeviceInfoList**"}) PointerPointer pointerPointer);

    public static native void avdevice_free_list_devices(@ByPtrPtr AVDeviceInfoList aVDeviceInfoList);

    @Cast({"const char*"})
    public static native BytePointer avdevice_license();

    public static native int avdevice_list_devices(AVFormatContext aVFormatContext, @Cast({"AVDeviceInfoList**"}) PointerPointer pointerPointer);

    public static native int avdevice_list_devices(AVFormatContext aVFormatContext, @ByPtrPtr AVDeviceInfoList aVDeviceInfoList);

    public static native int avdevice_list_input_sources(AVInputFormat aVInputFormat, String str, AVDictionary aVDictionary, @ByPtrPtr AVDeviceInfoList aVDeviceInfoList);

    public static native int avdevice_list_input_sources(AVInputFormat aVInputFormat, @Cast({"const char*"}) BytePointer bytePointer, AVDictionary aVDictionary, @Cast({"AVDeviceInfoList**"}) PointerPointer pointerPointer);

    public static native int avdevice_list_input_sources(AVInputFormat aVInputFormat, @Cast({"const char*"}) BytePointer bytePointer, AVDictionary aVDictionary, @ByPtrPtr AVDeviceInfoList aVDeviceInfoList);

    public static native int avdevice_list_output_sinks(AVOutputFormat aVOutputFormat, String str, AVDictionary aVDictionary, @ByPtrPtr AVDeviceInfoList aVDeviceInfoList);

    public static native int avdevice_list_output_sinks(AVOutputFormat aVOutputFormat, @Cast({"const char*"}) BytePointer bytePointer, AVDictionary aVDictionary, @Cast({"AVDeviceInfoList**"}) PointerPointer pointerPointer);

    public static native int avdevice_list_output_sinks(AVOutputFormat aVOutputFormat, @Cast({"const char*"}) BytePointer bytePointer, AVDictionary aVDictionary, @ByPtrPtr AVDeviceInfoList aVDeviceInfoList);

    public static native void avdevice_register_all();

    @Cast({"unsigned"})
    public static native int avdevice_version();

    static {
        Loader.load();
    }
}

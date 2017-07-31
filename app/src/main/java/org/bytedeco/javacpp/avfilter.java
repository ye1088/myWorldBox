package org.bytedeco.javacpp;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import org.bytedeco.javacpp.annotation.ByPtrPtr;
import org.bytedeco.javacpp.annotation.ByRef;
import org.bytedeco.javacpp.annotation.ByVal;
import org.bytedeco.javacpp.annotation.Cast;
import org.bytedeco.javacpp.annotation.Const;
import org.bytedeco.javacpp.annotation.MemberGetter;
import org.bytedeco.javacpp.annotation.Opaque;
import org.bytedeco.javacpp.avutil.AVClass;
import org.bytedeco.javacpp.avutil.AVDictionary;
import org.bytedeco.javacpp.avutil.AVFrame;
import org.bytedeco.javacpp.avutil.AVRational;

public class avfilter extends org.bytedeco.javacpp.presets.avfilter {
    public static final int AVFILTER_ALIGN = 16;
    public static final int AVFILTER_AUTO_CONVERT_ALL = 0;
    public static final int AVFILTER_AUTO_CONVERT_NONE = -1;
    public static final int AVFILTER_CMD_FLAG_FAST = 2;
    public static final int AVFILTER_CMD_FLAG_ONE = 1;
    public static final int AVFILTER_FLAG_DYNAMIC_INPUTS = 1;
    public static final int AVFILTER_FLAG_DYNAMIC_OUTPUTS = 2;
    public static final int AVFILTER_FLAG_SLICE_THREADS = 4;
    public static final int AVFILTER_FLAG_SUPPORT_TIMELINE = 196608;
    public static final int AVFILTER_FLAG_SUPPORT_TIMELINE_GENERIC = 65536;
    public static final int AVFILTER_FLAG_SUPPORT_TIMELINE_INTERNAL = 131072;
    public static final int AVFILTER_THREAD_SLICE = 1;
    public static final int AV_BUFFERSINK_FLAG_NO_REQUEST = 2;
    public static final int AV_BUFFERSINK_FLAG_PEEK = 1;
    public static final int AV_BUFFERSRC_FLAG_KEEP_REF = 8;
    public static final int AV_BUFFERSRC_FLAG_NO_CHECK_FORMAT = 1;
    public static final int AV_BUFFERSRC_FLAG_NO_COPY = 2;
    public static final int AV_BUFFERSRC_FLAG_PUSH = 4;
    public static final int AV_PERM_ALIGN = 64;
    public static final int AV_PERM_NEG_LINESIZES = 32;
    public static final int AV_PERM_PRESERVE = 4;
    public static final int AV_PERM_READ = 1;
    public static final int AV_PERM_REUSE = 8;
    public static final int AV_PERM_REUSE2 = 16;
    public static final int AV_PERM_WRITE = 2;

    public static class AVABufferSinkParams extends Pointer {
        private native void allocate();

        private native void allocateArray(int i);

        public native int all_channel_counts();

        public native AVABufferSinkParams all_channel_counts(int i);

        @MemberGetter
        @Const
        public native IntPointer channel_counts();

        @MemberGetter
        @Const
        public native LongPointer channel_layouts();

        @MemberGetter
        @Cast({"const AVSampleFormat*"})
        public native IntPointer sample_fmts();

        public native IntPointer sample_rates();

        public native AVABufferSinkParams sample_rates(IntPointer intPointer);

        static {
            Loader.load();
        }

        public AVABufferSinkParams() {
            allocate();
        }

        public AVABufferSinkParams(int size) {
            allocateArray(size);
        }

        public AVABufferSinkParams(Pointer p) {
            super(p);
        }

        public AVABufferSinkParams position(int position) {
            return (AVABufferSinkParams) super.position(position);
        }
    }

    public static class AVBufferSinkParams extends Pointer {
        private native void allocate();

        private native void allocateArray(int i);

        @MemberGetter
        @Cast({"const AVPixelFormat*"})
        public native IntPointer pixel_fmts();

        static {
            Loader.load();
        }

        public AVBufferSinkParams() {
            allocate();
        }

        public AVBufferSinkParams(int size) {
            allocateArray(size);
        }

        public AVBufferSinkParams(Pointer p) {
            super(p);
        }

        public AVBufferSinkParams position(int position) {
            return (AVBufferSinkParams) super.position(position);
        }
    }

    public static class AVFilter extends Pointer {
        private native void allocate();

        private native void allocateArray(int i);

        @MemberGetter
        @Cast({"const char*"})
        public native BytePointer description();

        public native int flags();

        public native AVFilter flags(int i);

        public native Init_AVFilterContext init();

        public native AVFilter init(Init_AVFilterContext init_AVFilterContext);

        public native Init_dict_AVFilterContext_PointerPointer init_dict();

        public native AVFilter init_dict(Init_dict_AVFilterContext_PointerPointer init_dict_AVFilterContext_PointerPointer);

        public native Init_opaque_AVFilterContext_Pointer init_opaque();

        public native AVFilter init_opaque(Init_opaque_AVFilterContext_Pointer init_opaque_AVFilterContext_Pointer);

        @MemberGetter
        @Const
        public native AVFilterPad inputs();

        @MemberGetter
        @Cast({"const char*"})
        public native BytePointer name();

        public native AVFilter next();

        public native AVFilter next(AVFilter aVFilter);

        @MemberGetter
        @Const
        public native AVFilterPad outputs();

        @MemberGetter
        @Const
        public native AVClass priv_class();

        public native int priv_size();

        public native AVFilter priv_size(int i);

        public native Process_command_AVFilterContext_BytePointer_BytePointer_BytePointer_int_int process_command();

        public native AVFilter process_command(Process_command_AVFilterContext_BytePointer_BytePointer_BytePointer_int_int process_command_AVFilterContext_BytePointer_BytePointer_BytePointer_int_int);

        public native Query_formats_AVFilterContext query_formats();

        public native AVFilter query_formats(Query_formats_AVFilterContext query_formats_AVFilterContext);

        public native Uninit_AVFilterContext uninit();

        public native AVFilter uninit(Uninit_AVFilterContext uninit_AVFilterContext);

        static {
            Loader.load();
        }

        public AVFilter() {
            allocate();
        }

        public AVFilter(int size) {
            allocateArray(size);
        }

        public AVFilter(Pointer p) {
            super(p);
        }

        public AVFilter position(int position) {
            return (AVFilter) super.position(position);
        }
    }

    public static class AVFilterBuffer extends Pointer {
        private native void allocate();

        private native void allocateArray(int i);

        @Cast({"uint8_t*"})
        public native BytePointer data(int i);

        @MemberGetter
        @Cast({"uint8_t**"})
        public native PointerPointer data();

        public native AVFilterBuffer data(int i, BytePointer bytePointer);

        @Cast({"uint8_t*"})
        public native BytePointer extended_data(int i);

        @MemberGetter
        @Cast({"uint8_t**"})
        public native PointerPointer extended_data();

        public native AVFilterBuffer extended_data(int i, BytePointer bytePointer);

        public native int format();

        public native AVFilterBuffer format(int i);

        public native Free_AVFilterBuffer free();

        public native AVFilterBuffer free(Free_AVFilterBuffer free_AVFilterBuffer);

        public native int h();

        public native AVFilterBuffer h(int i);

        public native int linesize(int i);

        @MemberGetter
        public native IntPointer linesize();

        public native AVFilterBuffer linesize(int i, int i2);

        public native Pointer priv();

        public native AVFilterBuffer priv(Pointer pointer);

        @Cast({"unsigned"})
        public native int refcount();

        public native AVFilterBuffer refcount(int i);

        public native int w();

        public native AVFilterBuffer w(int i);

        static {
            Loader.load();
        }

        public AVFilterBuffer() {
            allocate();
        }

        public AVFilterBuffer(int size) {
            allocateArray(size);
        }

        public AVFilterBuffer(Pointer p) {
            super(p);
        }

        public AVFilterBuffer position(int position) {
            return (AVFilterBuffer) super.position(position);
        }
    }

    public static class AVFilterBufferRef extends Pointer {
        private native void allocate();

        private native void allocateArray(int i);

        public native AVFilterBufferRef audio(AVFilterBufferRefAudioProps aVFilterBufferRefAudioProps);

        public native AVFilterBufferRefAudioProps audio();

        public native AVFilterBuffer buf();

        public native AVFilterBufferRef buf(AVFilterBuffer aVFilterBuffer);

        @Cast({"uint8_t*"})
        public native BytePointer data(int i);

        @MemberGetter
        @Cast({"uint8_t**"})
        public native PointerPointer data();

        public native AVFilterBufferRef data(int i, BytePointer bytePointer);

        @Cast({"uint8_t*"})
        public native BytePointer extended_data(int i);

        @MemberGetter
        @Cast({"uint8_t**"})
        public native PointerPointer extended_data();

        public native AVFilterBufferRef extended_data(int i, BytePointer bytePointer);

        public native int format();

        public native AVFilterBufferRef format(int i);

        public native int linesize(int i);

        @MemberGetter
        public native IntPointer linesize();

        public native AVFilterBufferRef linesize(int i, int i2);

        public native AVFilterBufferRef metadata(AVDictionary aVDictionary);

        public native AVDictionary metadata();

        public native int perms();

        public native AVFilterBufferRef perms(int i);

        public native long pos();

        public native AVFilterBufferRef pos(long j);

        public native long pts();

        public native AVFilterBufferRef pts(long j);

        @Cast({"AVMediaType"})
        public native int type();

        public native AVFilterBufferRef type(int i);

        public native AVFilterBufferRef video(AVFilterBufferRefVideoProps aVFilterBufferRefVideoProps);

        public native AVFilterBufferRefVideoProps video();

        static {
            Loader.load();
        }

        public AVFilterBufferRef() {
            allocate();
        }

        public AVFilterBufferRef(int size) {
            allocateArray(size);
        }

        public AVFilterBufferRef(Pointer p) {
            super(p);
        }

        public AVFilterBufferRef position(int position) {
            return (AVFilterBufferRef) super.position(position);
        }
    }

    public static class AVFilterBufferRefAudioProps extends Pointer {
        private native void allocate();

        private native void allocateArray(int i);

        @Cast({"uint64_t"})
        public native long channel_layout();

        public native AVFilterBufferRefAudioProps channel_layout(long j);

        public native int channels();

        public native AVFilterBufferRefAudioProps channels(int i);

        public native int nb_samples();

        public native AVFilterBufferRefAudioProps nb_samples(int i);

        public native int sample_rate();

        public native AVFilterBufferRefAudioProps sample_rate(int i);

        static {
            Loader.load();
        }

        public AVFilterBufferRefAudioProps() {
            allocate();
        }

        public AVFilterBufferRefAudioProps(int size) {
            allocateArray(size);
        }

        public AVFilterBufferRefAudioProps(Pointer p) {
            super(p);
        }

        public AVFilterBufferRefAudioProps position(int position) {
            return (AVFilterBufferRefAudioProps) super.position(position);
        }
    }

    public static class AVFilterBufferRefVideoProps extends Pointer {
        private native void allocate();

        private native void allocateArray(int i);

        public native int h();

        public native AVFilterBufferRefVideoProps h(int i);

        public native int interlaced();

        public native AVFilterBufferRefVideoProps interlaced(int i);

        public native int key_frame();

        public native AVFilterBufferRefVideoProps key_frame(int i);

        @Cast({"AVPictureType"})
        public native int pict_type();

        public native AVFilterBufferRefVideoProps pict_type(int i);

        public native BytePointer qp_table();

        public native AVFilterBufferRefVideoProps qp_table(BytePointer bytePointer);

        public native int qp_table_linesize();

        public native AVFilterBufferRefVideoProps qp_table_linesize(int i);

        public native int qp_table_size();

        public native AVFilterBufferRefVideoProps qp_table_size(int i);

        public native AVFilterBufferRefVideoProps sample_aspect_ratio(AVRational aVRational);

        @ByRef
        public native AVRational sample_aspect_ratio();

        public native int top_field_first();

        public native AVFilterBufferRefVideoProps top_field_first(int i);

        public native int w();

        public native AVFilterBufferRefVideoProps w(int i);

        static {
            Loader.load();
        }

        public AVFilterBufferRefVideoProps() {
            allocate();
        }

        public AVFilterBufferRefVideoProps(int size) {
            allocateArray(size);
        }

        public AVFilterBufferRefVideoProps(Pointer p) {
            super(p);
        }

        public AVFilterBufferRefVideoProps position(int position) {
            return (AVFilterBufferRefVideoProps) super.position(position);
        }
    }

    public static class AVFilterContext extends Pointer {
        private native void allocate();

        private native void allocateArray(int i);

        @MemberGetter
        @Const
        public native AVClass av_class();

        @Cast({"AVFilterCommand*"})
        public native Pointer command_queue();

        public native AVFilterContext command_queue(Pointer pointer);

        public native Pointer enable();

        public native AVFilterContext enable(Pointer pointer);

        @Cast({"char*"})
        public native BytePointer enable_str();

        public native AVFilterContext enable_str(BytePointer bytePointer);

        @MemberGetter
        @Const
        public native AVFilter filter();

        public native AVFilterContext graph(AVFilterGraph aVFilterGraph);

        public native AVFilterGraph graph();

        @Deprecated
        @Cast({"unsigned"})
        public native int input_count();

        public native AVFilterContext input_count(int i);

        public native AVFilterContext input_pads(AVFilterPad aVFilterPad);

        public native AVFilterPad input_pads();

        @MemberGetter
        @Cast({"AVFilterLink**"})
        public native PointerPointer inputs();

        public native AVFilterContext inputs(int i, AVFilterLink aVFilterLink);

        public native AVFilterLink inputs(int i);

        public native AVFilterContext internal(AVFilterInternal aVFilterInternal);

        public native AVFilterInternal internal();

        public native int is_disabled();

        public native AVFilterContext is_disabled(int i);

        @Cast({"char*"})
        public native BytePointer name();

        public native AVFilterContext name(BytePointer bytePointer);

        @Cast({"unsigned"})
        public native int nb_inputs();

        public native AVFilterContext nb_inputs(int i);

        @Cast({"unsigned"})
        public native int nb_outputs();

        public native AVFilterContext nb_outputs(int i);

        @Deprecated
        @Cast({"unsigned"})
        public native int output_count();

        public native AVFilterContext output_count(int i);

        public native AVFilterContext output_pads(AVFilterPad aVFilterPad);

        public native AVFilterPad output_pads();

        @MemberGetter
        @Cast({"AVFilterLink**"})
        public native PointerPointer outputs();

        public native AVFilterContext outputs(int i, AVFilterLink aVFilterLink);

        public native AVFilterLink outputs(int i);

        public native Pointer priv();

        public native AVFilterContext priv(Pointer pointer);

        public native int thread_type();

        public native AVFilterContext thread_type(int i);

        public native DoublePointer var_values();

        public native AVFilterContext var_values(DoublePointer doublePointer);

        static {
            Loader.load();
        }

        public AVFilterContext() {
            allocate();
        }

        public AVFilterContext(int size) {
            allocateArray(size);
        }

        public AVFilterContext(Pointer p) {
            super(p);
        }

        public AVFilterContext position(int position) {
            return (AVFilterContext) super.position(position);
        }
    }

    @Opaque
    public static class AVFilterFormats extends Pointer {
        public AVFilterFormats(Pointer p) {
            super(p);
        }
    }

    public static class AVFilterGraph extends Pointer {
        private native void allocate();

        private native void allocateArray(int i);

        @Cast({"char*"})
        public native BytePointer aresample_swr_opts();

        public native AVFilterGraph aresample_swr_opts(BytePointer bytePointer);

        @MemberGetter
        @Const
        public native AVClass av_class();

        @Cast({"unsigned"})
        public native int disable_auto_convert();

        public native AVFilterGraph disable_auto_convert(int i);

        public native AVFilterGraph execute(avfilter_execute_func org_bytedeco_javacpp_avfilter_avfilter_execute_func);

        public native avfilter_execute_func execute();

        @Deprecated
        @Cast({"unsigned"})
        public native int filter_count_unused();

        public native AVFilterGraph filter_count_unused(int i);

        @MemberGetter
        @Cast({"AVFilterContext**"})
        public native PointerPointer filters();

        public native AVFilterContext filters(int i);

        public native AVFilterGraph filters(int i, AVFilterContext aVFilterContext);

        public native AVFilterGraph internal(AVFilterGraphInternal aVFilterGraphInternal);

        public native AVFilterGraphInternal internal();

        @Cast({"unsigned"})
        public native int nb_filters();

        public native AVFilterGraph nb_filters(int i);

        public native int nb_threads();

        public native AVFilterGraph nb_threads(int i);

        public native Pointer opaque();

        public native AVFilterGraph opaque(Pointer pointer);

        @Cast({"char*"})
        public native BytePointer resample_lavr_opts();

        public native AVFilterGraph resample_lavr_opts(BytePointer bytePointer);

        @Cast({"char*"})
        public native BytePointer scale_sws_opts();

        public native AVFilterGraph scale_sws_opts(BytePointer bytePointer);

        @MemberGetter
        @Cast({"AVFilterLink**"})
        public native PointerPointer sink_links();

        public native AVFilterGraph sink_links(int i, AVFilterLink aVFilterLink);

        public native AVFilterLink sink_links(int i);

        public native int sink_links_count();

        public native AVFilterGraph sink_links_count(int i);

        public native int thread_type();

        public native AVFilterGraph thread_type(int i);

        static {
            Loader.load();
        }

        public AVFilterGraph() {
            allocate();
        }

        public AVFilterGraph(int size) {
            allocateArray(size);
        }

        public AVFilterGraph(Pointer p) {
            super(p);
        }

        public AVFilterGraph position(int position) {
            return (AVFilterGraph) super.position(position);
        }
    }

    @Opaque
    public static class AVFilterGraphInternal extends Pointer {
        public AVFilterGraphInternal(Pointer p) {
            super(p);
        }
    }

    public static class AVFilterInOut extends Pointer {
        private native void allocate();

        private native void allocateArray(int i);

        public native AVFilterContext filter_ctx();

        public native AVFilterInOut filter_ctx(AVFilterContext aVFilterContext);

        @Cast({"char*"})
        public native BytePointer name();

        public native AVFilterInOut name(BytePointer bytePointer);

        public native AVFilterInOut next();

        public native AVFilterInOut next(AVFilterInOut aVFilterInOut);

        public native int pad_idx();

        public native AVFilterInOut pad_idx(int i);

        static {
            Loader.load();
        }

        public AVFilterInOut() {
            allocate();
        }

        public AVFilterInOut(int size) {
            allocateArray(size);
        }

        public AVFilterInOut(Pointer p) {
            super(p);
        }

        public AVFilterInOut position(int position) {
            return (AVFilterInOut) super.position(position);
        }
    }

    @Opaque
    public static class AVFilterInternal extends Pointer {
        public AVFilterInternal(Pointer p) {
            super(p);
        }
    }

    public static class AVFilterLink extends Pointer {
        public static final int AVLINK_INIT = 2;
        public static final int AVLINK_STARTINIT = 1;
        public static final int AVLINK_UNINIT = 0;

        private native void allocate();

        private native void allocateArray(int i);

        public native int age_index();

        public native AVFilterLink age_index(int i);

        @Cast({"uint64_t"})
        public native long channel_layout();

        public native AVFilterLink channel_layout(long j);

        public native int channels();

        public native AVFilterLink channels(int i);

        public native int closed();

        public native AVFilterLink closed(int i);

        public native AVFilterBufferRef cur_buf_copy();

        public native AVFilterLink cur_buf_copy(AVFilterBufferRef aVFilterBufferRef);

        public native long current_pts();

        public native AVFilterLink current_pts(long j);

        public native AVFilterContext dst();

        public native AVFilterLink dst(AVFilterContext aVFilterContext);

        public native AVFilterLink dstpad(AVFilterPad aVFilterPad);

        public native AVFilterPad dstpad();

        @Cast({"unsigned"})
        public native int flags();

        public native AVFilterLink flags(int i);

        public native int format();

        public native AVFilterLink format(int i);

        public native long frame_count();

        public native AVFilterLink frame_count(long j);

        public native AVFilterLink frame_rate(AVRational aVRational);

        @ByRef
        public native AVRational frame_rate();

        @Cast({"unsigned"})
        public native int frame_requested();

        public native AVFilterLink frame_requested(int i);

        public native AVFilterGraph graph();

        public native AVFilterLink graph(AVFilterGraph aVFilterGraph);

        public native int h();

        public native AVFilterLink h(int i);

        @Cast({"AVFilterChannelLayouts*"})
        public native Pointer in_channel_layouts();

        public native AVFilterLink in_channel_layouts(Pointer pointer);

        public native AVFilterFormats in_formats();

        public native AVFilterLink in_formats(AVFilterFormats aVFilterFormats);

        public native AVFilterFormats in_samplerates();

        public native AVFilterLink in_samplerates(AVFilterFormats aVFilterFormats);

        public native int max_samples();

        public native AVFilterLink max_samples(int i);

        public native int min_samples();

        public native AVFilterLink min_samples(int i);

        @Cast({"AVFilterChannelLayouts*"})
        public native Pointer out_channel_layouts();

        public native AVFilterLink out_channel_layouts(Pointer pointer);

        public native AVFilterFormats out_formats();

        public native AVFilterLink out_formats(AVFilterFormats aVFilterFormats);

        public native AVFilterFormats out_samplerates();

        public native AVFilterLink out_samplerates(AVFilterFormats aVFilterFormats);

        public native AVFilterLink partial_buf(AVFrame aVFrame);

        public native AVFrame partial_buf();

        public native int partial_buf_size();

        public native AVFilterLink partial_buf_size(int i);

        @Cast({"AVFilterPool*"})
        public native Pointer pool();

        public native AVFilterLink pool(Pointer pointer);

        public native int request_samples();

        public native AVFilterLink request_samples(int i);

        public native AVFilterLink sample_aspect_ratio(AVRational aVRational);

        @ByRef
        public native AVRational sample_aspect_ratio();

        public native int sample_rate();

        public native AVFilterLink sample_rate(int i);

        public native AVFilterContext src();

        public native AVFilterLink src(AVFilterContext aVFilterContext);

        public native AVFilterLink srcpad(AVFilterPad aVFilterPad);

        public native AVFilterPad srcpad();

        public native AVFilterLink time_base(AVRational aVRational);

        @ByRef
        public native AVRational time_base();

        @Cast({"AVMediaType"})
        public native int type();

        public native AVFilterLink type(int i);

        public native int w();

        public native AVFilterLink w(int i);

        static {
            Loader.load();
        }

        public AVFilterLink() {
            allocate();
        }

        public AVFilterLink(int size) {
            allocateArray(size);
        }

        public AVFilterLink(Pointer p) {
            super(p);
        }

        public AVFilterLink position(int position) {
            return (AVFilterLink) super.position(position);
        }
    }

    public static class AVFilterPad extends Pointer {
        private native void allocate();

        private native void allocateArray(int i);

        public native Config_props_AVFilterLink config_props();

        public native AVFilterPad config_props(Config_props_AVFilterLink config_props_AVFilterLink);

        public native Draw_slice_AVFilterLink_int_int_int draw_slice();

        public native AVFilterPad draw_slice(Draw_slice_AVFilterLink_int_int_int draw_slice_AVFilterLink_int_int_int);

        public native End_frame_AVFilterLink end_frame();

        public native AVFilterPad end_frame(End_frame_AVFilterLink end_frame_AVFilterLink);

        public native Filter_frame_AVFilterLink_AVFrame filter_frame();

        public native AVFilterPad filter_frame(Filter_frame_AVFilterLink_AVFrame filter_frame_AVFilterLink_AVFrame);

        public native Get_audio_buffer_AVFilterLink_int get_audio_buffer();

        public native AVFilterPad get_audio_buffer(Get_audio_buffer_AVFilterLink_int get_audio_buffer_AVFilterLink_int);

        public native Get_video_buffer_AVFilterLink_int_int get_video_buffer();

        public native AVFilterPad get_video_buffer(Get_video_buffer_AVFilterLink_int_int get_video_buffer_AVFilterLink_int_int);

        @Deprecated
        public native int min_perms();

        public native AVFilterPad min_perms(int i);

        @MemberGetter
        @Cast({"const char*"})
        public native BytePointer name();

        public native int needs_fifo();

        public native AVFilterPad needs_fifo(int i);

        public native int needs_writable();

        public native AVFilterPad needs_writable(int i);

        public native Poll_frame_AVFilterLink poll_frame();

        public native AVFilterPad poll_frame(Poll_frame_AVFilterLink poll_frame_AVFilterLink);

        @Deprecated
        public native int rej_perms();

        public native AVFilterPad rej_perms(int i);

        public native Request_frame_AVFilterLink request_frame();

        public native AVFilterPad request_frame(Request_frame_AVFilterLink request_frame_AVFilterLink);

        public native Start_frame_AVFilterLink_AVFilterBufferRef start_frame();

        public native AVFilterPad start_frame(Start_frame_AVFilterLink_AVFilterBufferRef start_frame_AVFilterLink_AVFilterBufferRef);

        @Cast({"AVMediaType"})
        public native int type();

        public native AVFilterPad type(int i);

        static {
            Loader.load();
        }

        public AVFilterPad() {
            allocate();
        }

        public AVFilterPad(int size) {
            allocateArray(size);
        }

        public AVFilterPad(Pointer p) {
            super(p);
        }

        public AVFilterPad position(int position) {
            return (AVFilterPad) super.position(position);
        }
    }

    public static native AVABufferSinkParams av_abuffersink_params_alloc();

    @Deprecated
    public static native int av_buffersink_get_buffer_ref(AVFilterContext aVFilterContext, @Cast({"AVFilterBufferRef**"}) PointerPointer pointerPointer, int i);

    @Deprecated
    public static native int av_buffersink_get_buffer_ref(AVFilterContext aVFilterContext, @ByPtrPtr AVFilterBufferRef aVFilterBufferRef, int i);

    public static native int av_buffersink_get_frame(AVFilterContext aVFilterContext, AVFrame aVFrame);

    public static native int av_buffersink_get_frame_flags(AVFilterContext aVFilterContext, AVFrame aVFrame, int i);

    @ByVal
    public static native AVRational av_buffersink_get_frame_rate(AVFilterContext aVFilterContext);

    public static native int av_buffersink_get_samples(AVFilterContext aVFilterContext, AVFrame aVFrame, int i);

    public static native AVBufferSinkParams av_buffersink_params_alloc();

    @Deprecated
    public static native int av_buffersink_poll_frame(AVFilterContext aVFilterContext);

    @Deprecated
    public static native int av_buffersink_read(AVFilterContext aVFilterContext, @Cast({"AVFilterBufferRef**"}) PointerPointer pointerPointer);

    @Deprecated
    public static native int av_buffersink_read(AVFilterContext aVFilterContext, @ByPtrPtr AVFilterBufferRef aVFilterBufferRef);

    @Deprecated
    public static native int av_buffersink_read_samples(AVFilterContext aVFilterContext, @Cast({"AVFilterBufferRef**"}) PointerPointer pointerPointer, int i);

    @Deprecated
    public static native int av_buffersink_read_samples(AVFilterContext aVFilterContext, @ByPtrPtr AVFilterBufferRef aVFilterBufferRef, int i);

    public static native void av_buffersink_set_frame_size(AVFilterContext aVFilterContext, @Cast({"unsigned"}) int i);

    public static native int av_buffersrc_add_frame(AVFilterContext aVFilterContext, AVFrame aVFrame);

    public static native int av_buffersrc_add_frame_flags(AVFilterContext aVFilterContext, AVFrame aVFrame, int i);

    public static native int av_buffersrc_add_ref(AVFilterContext aVFilterContext, AVFilterBufferRef aVFilterBufferRef, int i);

    @Deprecated
    public static native int av_buffersrc_buffer(AVFilterContext aVFilterContext, AVFilterBufferRef aVFilterBufferRef);

    @Cast({"unsigned"})
    public static native int av_buffersrc_get_nb_failed_requests(AVFilterContext aVFilterContext);

    public static native int av_buffersrc_write_frame(AVFilterContext aVFilterContext, @Const AVFrame aVFrame);

    @Deprecated
    @Cast({"AVFilter**"})
    public static native PointerPointer av_filter_next(@Cast({"AVFilter**"}) PointerPointer pointerPointer);

    @ByPtrPtr
    @Deprecated
    public static native AVFilter av_filter_next(@ByPtrPtr AVFilter aVFilter);

    public static native int avfilter_config_links(AVFilterContext aVFilterContext);

    @Cast({"const char*"})
    public static native BytePointer avfilter_configuration();

    @Deprecated
    public static native int avfilter_copy_buf_props(AVFrame aVFrame, @Const AVFilterBufferRef aVFilterBufferRef);

    @Deprecated
    public static native void avfilter_copy_buffer_ref_props(AVFilterBufferRef aVFilterBufferRef, @Const AVFilterBufferRef aVFilterBufferRef2);

    @Deprecated
    public static native int avfilter_copy_frame_props(AVFilterBufferRef aVFilterBufferRef, @Const AVFrame aVFrame);

    public static native void avfilter_free(AVFilterContext aVFilterContext);

    @Deprecated
    public static native AVFilterBufferRef avfilter_get_audio_buffer_ref_from_arrays(@ByPtrPtr @Cast({"uint8_t**"}) ByteBuffer byteBuffer, int i, int i2, int i3, @Cast({"AVSampleFormat"}) int i4, @Cast({"uint64_t"}) long j);

    @Deprecated
    public static native AVFilterBufferRef avfilter_get_audio_buffer_ref_from_arrays(@ByPtrPtr @Cast({"uint8_t**"}) BytePointer bytePointer, int i, int i2, int i3, @Cast({"AVSampleFormat"}) int i4, @Cast({"uint64_t"}) long j);

    @Deprecated
    public static native AVFilterBufferRef avfilter_get_audio_buffer_ref_from_arrays(@Cast({"uint8_t**"}) PointerPointer pointerPointer, int i, int i2, int i3, @Cast({"AVSampleFormat"}) int i4, @Cast({"uint64_t"}) long j);

    @Deprecated
    public static native AVFilterBufferRef avfilter_get_audio_buffer_ref_from_arrays(@ByPtrPtr @Cast({"uint8_t**"}) byte[] bArr, int i, int i2, int i3, @Cast({"AVSampleFormat"}) int i4, @Cast({"uint64_t"}) long j);

    @Deprecated
    public static native AVFilterBufferRef avfilter_get_audio_buffer_ref_from_arrays_channels(@ByPtrPtr @Cast({"uint8_t**"}) ByteBuffer byteBuffer, int i, int i2, int i3, @Cast({"AVSampleFormat"}) int i4, int i5, @Cast({"uint64_t"}) long j);

    @Deprecated
    public static native AVFilterBufferRef avfilter_get_audio_buffer_ref_from_arrays_channels(@ByPtrPtr @Cast({"uint8_t**"}) BytePointer bytePointer, int i, int i2, int i3, @Cast({"AVSampleFormat"}) int i4, int i5, @Cast({"uint64_t"}) long j);

    @Deprecated
    public static native AVFilterBufferRef avfilter_get_audio_buffer_ref_from_arrays_channels(@Cast({"uint8_t**"}) PointerPointer pointerPointer, int i, int i2, int i3, @Cast({"AVSampleFormat"}) int i4, int i5, @Cast({"uint64_t"}) long j);

    @Deprecated
    public static native AVFilterBufferRef avfilter_get_audio_buffer_ref_from_arrays_channels(@ByPtrPtr @Cast({"uint8_t**"}) byte[] bArr, int i, int i2, int i3, @Cast({"AVSampleFormat"}) int i4, int i5, @Cast({"uint64_t"}) long j);

    @Const
    public static native AVFilter avfilter_get_by_name(String str);

    @Const
    public static native AVFilter avfilter_get_by_name(@Cast({"const char*"}) BytePointer bytePointer);

    @Const
    public static native AVClass avfilter_get_class();

    @Deprecated
    public static native AVFilterBufferRef avfilter_get_video_buffer_ref_from_arrays(@ByPtrPtr @Cast({"uint8_t*const*"}) ByteBuffer byteBuffer, @Const IntBuffer intBuffer, int i, int i2, int i3, @Cast({"AVPixelFormat"}) int i4);

    @Deprecated
    public static native AVFilterBufferRef avfilter_get_video_buffer_ref_from_arrays(@ByPtrPtr @Cast({"uint8_t*const*"}) BytePointer bytePointer, @Const IntPointer intPointer, int i, int i2, int i3, @Cast({"AVPixelFormat"}) int i4);

    @Deprecated
    public static native AVFilterBufferRef avfilter_get_video_buffer_ref_from_arrays(@Cast({"uint8_t*const*"}) PointerPointer pointerPointer, @Const IntPointer intPointer, int i, int i2, int i3, @Cast({"AVPixelFormat"}) int i4);

    @Deprecated
    public static native AVFilterBufferRef avfilter_get_video_buffer_ref_from_arrays(@ByPtrPtr @Cast({"uint8_t*const*"}) byte[] bArr, @Const int[] iArr, int i, int i2, int i3, @Cast({"AVPixelFormat"}) int i4);

    @Deprecated
    public static native int avfilter_graph_add_filter(AVFilterGraph aVFilterGraph, AVFilterContext aVFilterContext);

    public static native AVFilterGraph avfilter_graph_alloc();

    public static native AVFilterContext avfilter_graph_alloc_filter(AVFilterGraph aVFilterGraph, @Const AVFilter aVFilter, String str);

    public static native AVFilterContext avfilter_graph_alloc_filter(AVFilterGraph aVFilterGraph, @Const AVFilter aVFilter, @Cast({"const char*"}) BytePointer bytePointer);

    public static native int avfilter_graph_config(AVFilterGraph aVFilterGraph, Pointer pointer);

    public static native int avfilter_graph_create_filter(@Cast({"AVFilterContext**"}) PointerPointer pointerPointer, @Const AVFilter aVFilter, @Cast({"const char*"}) BytePointer bytePointer, @Cast({"const char*"}) BytePointer bytePointer2, Pointer pointer, AVFilterGraph aVFilterGraph);

    public static native int avfilter_graph_create_filter(@ByPtrPtr AVFilterContext aVFilterContext, @Const AVFilter aVFilter, String str, String str2, Pointer pointer, AVFilterGraph aVFilterGraph);

    public static native int avfilter_graph_create_filter(@ByPtrPtr AVFilterContext aVFilterContext, @Const AVFilter aVFilter, @Cast({"const char*"}) BytePointer bytePointer, @Cast({"const char*"}) BytePointer bytePointer2, Pointer pointer, AVFilterGraph aVFilterGraph);

    @Cast({"char*"})
    public static native ByteBuffer avfilter_graph_dump(AVFilterGraph aVFilterGraph, String str);

    @Cast({"char*"})
    public static native BytePointer avfilter_graph_dump(AVFilterGraph aVFilterGraph, @Cast({"const char*"}) BytePointer bytePointer);

    public static native void avfilter_graph_free(@Cast({"AVFilterGraph**"}) PointerPointer pointerPointer);

    public static native void avfilter_graph_free(@ByPtrPtr AVFilterGraph aVFilterGraph);

    public static native AVFilterContext avfilter_graph_get_filter(AVFilterGraph aVFilterGraph, String str);

    public static native AVFilterContext avfilter_graph_get_filter(AVFilterGraph aVFilterGraph, @Cast({"const char*"}) BytePointer bytePointer);

    public static native int avfilter_graph_parse(AVFilterGraph aVFilterGraph, String str, AVFilterInOut aVFilterInOut, AVFilterInOut aVFilterInOut2, Pointer pointer);

    public static native int avfilter_graph_parse(AVFilterGraph aVFilterGraph, @Cast({"const char*"}) BytePointer bytePointer, AVFilterInOut aVFilterInOut, AVFilterInOut aVFilterInOut2, Pointer pointer);

    public static native int avfilter_graph_parse2(AVFilterGraph aVFilterGraph, String str, @ByPtrPtr AVFilterInOut aVFilterInOut, @ByPtrPtr AVFilterInOut aVFilterInOut2);

    public static native int avfilter_graph_parse2(AVFilterGraph aVFilterGraph, @Cast({"const char*"}) BytePointer bytePointer, @Cast({"AVFilterInOut**"}) PointerPointer pointerPointer, @Cast({"AVFilterInOut**"}) PointerPointer pointerPointer2);

    public static native int avfilter_graph_parse2(AVFilterGraph aVFilterGraph, @Cast({"const char*"}) BytePointer bytePointer, @ByPtrPtr AVFilterInOut aVFilterInOut, @ByPtrPtr AVFilterInOut aVFilterInOut2);

    public static native int avfilter_graph_parse_ptr(AVFilterGraph aVFilterGraph, String str, @ByPtrPtr AVFilterInOut aVFilterInOut, @ByPtrPtr AVFilterInOut aVFilterInOut2, Pointer pointer);

    public static native int avfilter_graph_parse_ptr(AVFilterGraph aVFilterGraph, @Cast({"const char*"}) BytePointer bytePointer, @Cast({"AVFilterInOut**"}) PointerPointer pointerPointer, @Cast({"AVFilterInOut**"}) PointerPointer pointerPointer2, Pointer pointer);

    public static native int avfilter_graph_parse_ptr(AVFilterGraph aVFilterGraph, @Cast({"const char*"}) BytePointer bytePointer, @ByPtrPtr AVFilterInOut aVFilterInOut, @ByPtrPtr AVFilterInOut aVFilterInOut2, Pointer pointer);

    public static native int avfilter_graph_queue_command(AVFilterGraph aVFilterGraph, String str, String str2, String str3, int i, double d);

    public static native int avfilter_graph_queue_command(AVFilterGraph aVFilterGraph, @Cast({"const char*"}) BytePointer bytePointer, @Cast({"const char*"}) BytePointer bytePointer2, @Cast({"const char*"}) BytePointer bytePointer3, int i, double d);

    public static native int avfilter_graph_request_oldest(AVFilterGraph aVFilterGraph);

    public static native int avfilter_graph_send_command(AVFilterGraph aVFilterGraph, String str, String str2, String str3, @Cast({"char*"}) ByteBuffer byteBuffer, int i, int i2);

    public static native int avfilter_graph_send_command(AVFilterGraph aVFilterGraph, String str, String str2, String str3, @Cast({"char*"}) BytePointer bytePointer, int i, int i2);

    public static native int avfilter_graph_send_command(AVFilterGraph aVFilterGraph, String str, String str2, String str3, @Cast({"char*"}) byte[] bArr, int i, int i2);

    public static native int avfilter_graph_send_command(AVFilterGraph aVFilterGraph, @Cast({"const char*"}) BytePointer bytePointer, @Cast({"const char*"}) BytePointer bytePointer2, @Cast({"const char*"}) BytePointer bytePointer3, @Cast({"char*"}) ByteBuffer byteBuffer, int i, int i2);

    public static native int avfilter_graph_send_command(AVFilterGraph aVFilterGraph, @Cast({"const char*"}) BytePointer bytePointer, @Cast({"const char*"}) BytePointer bytePointer2, @Cast({"const char*"}) BytePointer bytePointer3, @Cast({"char*"}) BytePointer bytePointer4, int i, int i2);

    public static native int avfilter_graph_send_command(AVFilterGraph aVFilterGraph, @Cast({"const char*"}) BytePointer bytePointer, @Cast({"const char*"}) BytePointer bytePointer2, @Cast({"const char*"}) BytePointer bytePointer3, @Cast({"char*"}) byte[] bArr, int i, int i2);

    public static native void avfilter_graph_set_auto_convert(AVFilterGraph aVFilterGraph, @Cast({"unsigned"}) int i);

    public static native int avfilter_init_dict(AVFilterContext aVFilterContext, @Cast({"AVDictionary**"}) PointerPointer pointerPointer);

    public static native int avfilter_init_dict(AVFilterContext aVFilterContext, @ByPtrPtr AVDictionary aVDictionary);

    @Deprecated
    public static native int avfilter_init_filter(AVFilterContext aVFilterContext, String str, Pointer pointer);

    @Deprecated
    public static native int avfilter_init_filter(AVFilterContext aVFilterContext, @Cast({"const char*"}) BytePointer bytePointer, Pointer pointer);

    public static native int avfilter_init_str(AVFilterContext aVFilterContext, String str);

    public static native int avfilter_init_str(AVFilterContext aVFilterContext, @Cast({"const char*"}) BytePointer bytePointer);

    public static native AVFilterInOut avfilter_inout_alloc();

    public static native void avfilter_inout_free(@Cast({"AVFilterInOut**"}) PointerPointer pointerPointer);

    public static native void avfilter_inout_free(@ByPtrPtr AVFilterInOut aVFilterInOut);

    public static native int avfilter_insert_filter(AVFilterLink aVFilterLink, AVFilterContext aVFilterContext, @Cast({"unsigned"}) int i, @Cast({"unsigned"}) int i2);

    @Cast({"const char*"})
    public static native BytePointer avfilter_license();

    public static native int avfilter_link(AVFilterContext aVFilterContext, @Cast({"unsigned"}) int i, AVFilterContext aVFilterContext2, @Cast({"unsigned"}) int i2);

    public static native void avfilter_link_free(@Cast({"AVFilterLink**"}) PointerPointer pointerPointer);

    public static native void avfilter_link_free(@ByPtrPtr AVFilterLink aVFilterLink);

    public static native int avfilter_link_get_channels(AVFilterLink aVFilterLink);

    public static native void avfilter_link_set_closed(AVFilterLink aVFilterLink, int i);

    @Const
    public static native AVFilter avfilter_next(@Const AVFilter aVFilter);

    @Deprecated
    public static native int avfilter_open(@Cast({"AVFilterContext**"}) PointerPointer pointerPointer, AVFilter aVFilter, @Cast({"const char*"}) BytePointer bytePointer);

    @Deprecated
    public static native int avfilter_open(@ByPtrPtr AVFilterContext aVFilterContext, AVFilter aVFilter, String str);

    @Deprecated
    public static native int avfilter_open(@ByPtrPtr AVFilterContext aVFilterContext, AVFilter aVFilter, @Cast({"const char*"}) BytePointer bytePointer);

    public static native int avfilter_pad_count(@Const AVFilterPad aVFilterPad);

    @Cast({"const char*"})
    public static native BytePointer avfilter_pad_get_name(@Const AVFilterPad aVFilterPad, int i);

    @Cast({"AVMediaType"})
    public static native int avfilter_pad_get_type(@Const AVFilterPad aVFilterPad, int i);

    public static native int avfilter_process_command(AVFilterContext aVFilterContext, String str, String str2, @Cast({"char*"}) ByteBuffer byteBuffer, int i, int i2);

    public static native int avfilter_process_command(AVFilterContext aVFilterContext, String str, String str2, @Cast({"char*"}) BytePointer bytePointer, int i, int i2);

    public static native int avfilter_process_command(AVFilterContext aVFilterContext, String str, String str2, @Cast({"char*"}) byte[] bArr, int i, int i2);

    public static native int avfilter_process_command(AVFilterContext aVFilterContext, @Cast({"const char*"}) BytePointer bytePointer, @Cast({"const char*"}) BytePointer bytePointer2, @Cast({"char*"}) ByteBuffer byteBuffer, int i, int i2);

    public static native int avfilter_process_command(AVFilterContext aVFilterContext, @Cast({"const char*"}) BytePointer bytePointer, @Cast({"const char*"}) BytePointer bytePointer2, @Cast({"char*"}) BytePointer bytePointer3, int i, int i2);

    public static native int avfilter_process_command(AVFilterContext aVFilterContext, @Cast({"const char*"}) BytePointer bytePointer, @Cast({"const char*"}) BytePointer bytePointer2, @Cast({"char*"}) byte[] bArr, int i, int i2);

    @Deprecated
    public static native AVFilterBufferRef avfilter_ref_buffer(AVFilterBufferRef aVFilterBufferRef, int i);

    @Deprecated
    public static native int avfilter_ref_get_channels(AVFilterBufferRef aVFilterBufferRef);

    public static native int avfilter_register(AVFilter aVFilter);

    public static native void avfilter_register_all();

    @Deprecated
    public static native void avfilter_uninit();

    @Deprecated
    public static native void avfilter_unref_buffer(AVFilterBufferRef aVFilterBufferRef);

    @Deprecated
    public static native void avfilter_unref_bufferp(@Cast({"AVFilterBufferRef**"}) PointerPointer pointerPointer);

    @Deprecated
    public static native void avfilter_unref_bufferp(@ByPtrPtr AVFilterBufferRef aVFilterBufferRef);

    @Cast({"unsigned"})
    public static native int avfilter_version();

    static {
        Loader.load();
    }
}

import Testing
import TokioStream

@Suite("TokioStream Swift Export Suite")
struct TokioStreamExportTests {
    @Test("Swift module loads cleanly")
    func swiftModuleLoads() {
        #expect(Bool(true), "TokioStream swift module imported cleanly")
    }

    @Test("TokioStreamLib module info is accessible")
    func moduleInfo() {
        #expect(TokioStreamLib.shared.CRATE_NAME == "tokio_stream")
        #expect(TokioStreamLib.shared.MODULE_NAME == "tokio-stream")
    }
}

#if canImport(Testing)
import Testing
import TokioStream

@Suite struct TokioStreamExportTests {
    @Test func testSwiftModuleLoads() {
        #expect(TokioStreamLib().MODULE_NAME == "tokio-stream")
    }
}
#elseif canImport(XCTest)
import XCTest
import TokioStream

final class TokioStreamExportTests: XCTestCase {
    func testSwiftModuleLoads() throws {
        XCTAssertTrue(TokioStreamLib().MODULE_NAME == "tokio-stream", "TokioStream swift module imported cleanly")
    }
}
#endif

//
//  WebView.swift
//  iosApp
//
//  Created by Shubham Tomar on 27/06/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import WebKit

struct WebView: UIViewRepresentable {
    var urlRequest: URLRequest
    @Binding var contentHeight: CGFloat

    func makeUIView(context: UIViewRepresentableContext<WebView>) -> WKWebView {
        let webView = WKWebView()
        webView.isUserInteractionEnabled = false
        webView.scrollView.isScrollEnabled = false
        webView.navigationDelegate = context.coordinator
        return webView
    }

    func updateUIView(_ wkWebView: WKWebView, context: UIViewRepresentableContext<WebView>) {
        wkWebView.load(urlRequest)
    }

    func makeCoordinator() -> Coordinator {
        Coordinator(contentHeight: $contentHeight)
    }

    class Coordinator: NSObject, WKNavigationDelegate {
        @Binding var contentHeight: CGFloat
        var resized = false

        init(contentHeight: Binding<CGFloat>) {
            self._contentHeight = contentHeight
        }

        func webView(_ webView: WKWebView, didFinish navigation: WKNavigation!) {
            webView.evaluateJavaScript("document.readyState") { complete, _ in
                guard complete != nil else { return }
                
                // JavaScript to hide header and footer
                              let js = """
                              document.querySelector('header').style.display='none';
                              document.querySelector('footer').style.display='none';
                              document.body.scrollHeight
                              """
                
                webView.evaluateJavaScript(js) { height, _ in
                    guard let height = height as? CGFloat else { return }

                    if !self.resized {
                        self.contentHeight = height
                        self.resized = true
                    }
                }
            }
            
        }
    }
}
 

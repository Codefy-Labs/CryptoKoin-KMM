//
//  ChatInputField.swift
//  iosApp
//
//  Created by Shubham Tomar on 27/06/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import Foundation

struct ChatInputField: View {
    @Binding var text: String
    var replyingToUserName: String?
    var enableSendButton: Bool
    var onClearReplyTo: () -> Void
    var onClickSend: () -> Void
    
    var body: some View {
        VStack(spacing: 6) {
            if let replyingToUserName = replyingToUserName, !replyingToUserName.isEmpty {
                HStack {
                    Text("Replying to \(replyingToUserName)")
                        .customFont( 12, weight : .medium)
                        .foregroundColor(.white)
                    
                    Spacer()
                    
                    Button(action: onClearReplyTo) {
                        Image(systemName: "xmark.circle.fill")
                            .resizable()
                            .frame(width: 16, height: 16)
                            .foregroundColor(.white)
                    }
                }
                .padding(.horizontal, 14)
                .padding(.vertical, 6)
                .background(Color.accentColor) // Adjust color as needed
            }
            
            HStack(alignment: .bottom) {
                TextField("Add a comment...", text: $text, axis: .vertical)
                    .customFont( 14, weight : .medium)
                    .padding(.vertical, 10)
                   
                if enableSendButton {
                    Button(action: onClickSend) {
                        Image(systemName: "paperplane.fill")
                            .resizable()
                            .frame(width: 24, height: 24)
                    }
                    .padding(.bottom, 10)
                }
            }
            .padding(.horizontal, 16)
            .padding(.vertical, 6)
            
            Divider()
            
            CommunityRulesText(onLinkClick: {})
                .padding(.horizontal, 16)
                .padding(.bottom, 2)
        }
        
        .background(Color(UIColor.systemBackground))
        .cornerRadius(8)
        .shadow(color: .accentColor.opacity(0.1), radius: 8, x: 0, y: 4)
    }
}

struct CommunityRulesText: View {
    var onLinkClick: () -> Void
    
    var body: some View {
        HStack(spacing: 4) {
            Image(systemName: "info.circle.fill")
                .resizable()
                .frame(width: 12, height: 12)
                .foregroundColor(.primary.opacity(0.7))
            
            Text("Please follow ")
                .font(.custom(CustomFontWeight.regular.fontName, size: 10))
                .foregroundColor(.primary.opacity(0.7)) +
            Text("community rules")
                .font(.custom(CustomFontWeight.medium.fontName, size: 10))
                .foregroundColor(.accentColor) // Adjust color as needed
                .underline()
                +
            Text(" when commenting")
                .font(.custom(CustomFontWeight.regular.fontName, size: 10))
                .foregroundColor(.primary.opacity(0.7))
            Spacer()
        }.onTapGesture {
            onLinkClick()
        }
        .padding(.vertical, 6)
    }
}

struct ChatInputFieldPreview: View {
    @State private var text = ""
    
    var body: some View {
        VStack {
            Spacer()
            
            ChatInputField(
                text: $text,
                replyingToUserName: "ShubhamKodes",
                enableSendButton: true,
                onClearReplyTo: {
                    print("Clear Reply To")
                },
                onClickSend: {
                    print("Send Comment")
                }
            )
            .padding()
        }
        .background(Color(UIColor.systemBackground))
        .accentColor(.red)
    }
}

 

struct ChatInputFieldPreviewProvider: PreviewProvider {
    static var previews: some View {
        ChatInputFieldPreview()
    }
}

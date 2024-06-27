//
//  NewsDiscussionPage.swift
//  iosApp
//
//  Created by Shubham Tomar on 27/06/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation

import SwiftUI


struct DiscussionsView : View {
    
     private var data = generateDummyData()
    
    var body : some View {
        ZStack(alignment : .top){
            
            CommentSection(
                data: data,
                onClickReplyTo: { comment, reply in print("Reply to \(comment.id)") },
                onClickLike: { id in print("Like \(id)") }
            ) 
             
            VStack{
                Spacer()
                ChatInputField(
                    text: Binding(get: {""}, set: {value in }),
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
        }
    }
}

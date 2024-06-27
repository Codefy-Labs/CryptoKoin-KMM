//
//  CommentsSection.swift
//  iosApp
//
//  Created by Shubham Tomar on 27/06/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct CommentSection: View {
    var data: Discussions
    var onClickReplyTo: (Comment, Reply?) -> Void
    var onClickLike: (Int) -> Void
    
    var body: some View {
        List {
            ForEach(data.comments, id: \.id) { comment in
                CommentItem(
                    comment: comment,
                    onClickReplyTo: onClickReplyTo,
                    onClickLike: onClickLike
                )
                .listRowSeparator(.hidden)
                .listRowBackground(Color.clear)
                .listRowInsets(EdgeInsets())
                .listRowSpacing(16)
            }
            
            Spacer().frame(height: 50)
                .listRowSeparator(.hidden)
                .listRowBackground(Color.clear)
                .listRowInsets(EdgeInsets())
                .listRowSpacing(0)
            
        }.listStyle(.plain)
    }
}

struct CommentItem: View {
    var comment: Comment
    var onClickReplyTo: (Comment, Reply?) -> Void
    var onClickLike: (Int) -> Void
    
    @State private var viewAllReplies = false
    
    var body: some View {
        ZStack{
            VStack(alignment: .leading, spacing: 6) {
                UserInfoRow(user: comment.user, timestamp: comment.timestamp)
                Text(comment.comment)
                    .customFont(14, weight: .medium)
                LikesAndReplyRow(
                    likes: comment.likes,
                    repliesCount: comment.replies.count,
                    onLike: { onClickLike(comment.id) },
                    onClickReply: { onClickReplyTo(comment, nil) },
                    viewRepliesState: $viewAllReplies
                )
                
                if !comment.replies.isEmpty && viewAllReplies {
                    RepliesContainer(
                        replies: comment.replies,
                        onClickReplyTo: { reply in onClickReplyTo(comment, reply) }, onClickLike: onClickLike
                    )
                }
            }.padding(.top, 16)
                .padding(.horizontal, 16)
        }
    }
}

struct RepliesContainer: View {
    var replies: [Reply]
    var onClickReplyTo: (Reply) -> Void
    var onClickLike: (Int) -> Void
    
    var body: some View {
        HStack(alignment: .top) {
            
            VStack(alignment: .leading) {
                ForEach(replies, id: \.id) { reply in
                    ReplyItem(
                        reply: reply,
                        onLike: { onClickLike(reply.id) },
                        onClickReply: { onClickReplyTo(reply) }
                    )
                }
            } .background(
                GeometryReader { geometry in
                    DottedLine()
                        .stroke(style: StrokeStyle(lineWidth: 1, dash: [5, 3]))
                        .foregroundColor(.primary.opacity(0.3))
                        .frame(height: geometry.size.height - 12)
                        .offset(x: -geometry.size.width / 2 )
                }
            )
        }
        .padding(.leading, 16)
        .padding(.top, 8)
    }
}

struct ReplyItem: View {
    var reply: Reply
    var onLike: () -> Void
    var onClickReply: () -> Void
    
    var body: some View {
        VStack(alignment: .leading, spacing: 6) {
            UserInfoRow(user: reply.user, timestamp: reply.timestamp)
            Text(reply.comment)
                .customFont(14)
            LikesAndReplyRow(
                likes: reply.likes,
                repliesCount: 0,
                onLike: onLike,
                onClickReply: onClickReply,
                viewRepliesState : Binding(get: {false}, set: {value in })
            )
        }
        .padding(.vertical, 10)
        .padding(.leading, 16)
    }
}

struct UserInfoRow: View {
    var user: User
    var timestamp: String
    
    var body: some View {
        HStack {
            AsyncImage(url: URL(string: user.profilePicture)) { image in
                image.resizable()
                    .clipShape(Circle())
            } placeholder: {
                Circle().fill(Color.gray)
            }
            .frame(width: 30, height: 30)
            .clipShape(Circle())
            
            VStack(alignment: .leading) {
                Text(user.name)
                    .customFont(14)
                    .fontWeight(.semibold)
                    .foregroundColor(.primary.opacity(0.8))
                Text(timestamp)
                    .customFont(10)
                    .foregroundColor(.primary.opacity(0.8))
            }
        }
    }
}

struct DottedLine: Shape {
    func path(in rect: CGRect) -> Path {
        var path = Path()
        let dashHeight: CGFloat = 5
        let dashSpacing: CGFloat = 3
        
        for y in stride(from: 0, to: rect.height, by: dashHeight + dashSpacing) {
            path.move(to: CGPoint(x: rect.midX, y: y))
            path.addLine(to: CGPoint(x: rect.midX, y: y + dashHeight))
        }
        
        return path
    }
}

struct LikesAndReplyRow: View {
    var likes: Int
    var repliesCount: Int
    var onLike: () -> Void
    var onClickReply: () -> Void
    @Binding var viewRepliesState: Bool
    
    var body: some View {
        HStack(spacing : 0){
            Button(action: onLike) {
                Image( "ic-like")
                    .renderingMode(.template)
                    .resizable()
                    .frame(width: 16, height: 16)
                    .foregroundColor(.primary.opacity(0.7))
            }.padding(.trailing, 8)
            
            Text("\(likes) Likes")
                .font(.system(size: 12))
                .fontWeight(.semibold)
                .foregroundColor(.primary.opacity(0.7))
                .padding(.trailing, 12)
            
            Button(action: onClickReply) {
                HStack{
                    Image("ic-reply")
                        .renderingMode(.template)
                        .resizable()
                        .frame(width: 16, height: 16)
                        .tint(.primary.opacity(0.7))
                    Text("Reply")
                        .font(.system(size: 12))
                        .fontWeight(.semibold)
                        .foregroundColor(.primary.opacity(0.7))
                }
            }
            
            Spacer()
            
            if repliesCount > 0 {
                Button(action: {
                    withAnimation{
                        viewRepliesState.toggle()
                    }
                }) {
                    Text(viewRepliesState == true ? "Hide Replies" : "View \(repliesCount) more reply")
                        .font(.system(size: 12))
                        .fontWeight(.semibold)
                        .foregroundColor(.primary.opacity(0.7))
                }
            }
        }.padding(.top,4)
    }
}

// Sample Data Models
struct Discussions {
    var id: Int
    var userId: Int
    var likes: Int
    var commentsCount: Int
    var comments: [Comment]
}

struct Comment: Identifiable {
    var id: Int
    var likes : Int
    var userId: Int
    var comment: String
    var timestamp: String
    var user: User
    var replies: [Reply]
}

struct Reply: Identifiable {
    var id: Int
    var likes: Int
    var timestamp: String
    var user: User
    var comment: String
}

struct User: Identifiable {
    var id = UUID()
    var name: String
    var profilePicture: String
}

// Sample Data Generation
func generateDummyData() -> Discussions {
    let users = [
        User(name: "Maude Hall", profilePicture: "https://cdn-icons-png.freepik.com/512/3135/3135715.png"),
        User(name: "Dianne Russell", profilePicture: "https://cdn-icons-png.freepik.com/512/3135/3135789.png"),
        User(name: "Esther Howard", profilePicture: "https://a0.anyrgb.com/pngimg/1236/14/no-facial-features-no-avatar-no-eyes-expressionless-avatar-icon-delayering-avatar-user-avatar-men-head-portrait.png")
    ]
    
    let replies1 = [
        Reply(id: 1, likes: 10, timestamp: "10 min ago", user: users[2], comment: "This could be due to them taking their time to release a stable version."),
        Reply(id: 2, likes: 10, timestamp: "10 min ago", user: users[2], comment: "This could be due to them taking their time to release a stable version.")
    ]
    
    let comments = [
        Comment(id: 1, likes : 53,userId: 2, comment: "That's a fantastic new app feature. You and your team did an excellent job of incorporating user testing feedback.", timestamp: "14 min ago", user: users[0], replies: replies1),
        Comment(id: 2, likes : 22, userId: 1, comment: "But don't you think the timing is off because many other apps have done this even earlier, causing people to switch apps?", timestamp: "24 min ago", user: users[1], replies: replies1)
    ]
    
    return Discussions(id: 1, userId: 1, likes: 1, commentsCount: 2, comments: comments)
}

struct ChatPreview: View {
      private var data = generateDummyData()
    
    
    var body: some View {
        VStack{
            CommentSection(
                data: data,
                onClickReplyTo: { comment, reply in print("Reply to \(comment.id)") },
                onClickLike: { id in print("Like \(id)") }
            )
            .padding()
            Spacer()
        }
    }
}

struct ChatPreview_Previews: PreviewProvider {
    static var previews: some View {
        ChatPreview()
    }
}

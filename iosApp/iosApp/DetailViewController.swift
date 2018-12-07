
import Foundation
import UIKit
import app

class DetailViewController : UIViewController, TalkDetailView {
    
    var talkId: String!
    @IBOutlet weak var speakersLabel: UILabel!
    @IBOutlet weak var hoursLabel: UILabel!
    @IBOutlet weak var roomAndTechLabel: UILabel!
    @IBOutlet weak var descriptionTextView: UITextView!
    
    var button: UIButton = UIButton(type: .custom)
    
    private lazy var favoriteBarButtonItem: UIBarButtonItem = {
        self.button.setImage(UIImage(named: "star")?.withRenderingMode(.alwaysTemplate), for: UIControlState())
        self.button.addTarget(self, action: #selector(self.doFavButtonClick),for: .touchUpInside)
        self.button.sizeToFit()
        return UIBarButtonItem(customView: button)
    }()
    
    var presenter: TalkDetailPresenter!
    
    override func viewDidLoad() {
        descriptionTextView.textContainerInset = .zero
        descriptionTextView.textContainer.lineFragmentPadding = 0
        
        presenter = TalkDetailPresenter(view: self)
        presenter.onCreate()
        presenter.loadDetails(id: self.talkId)
        
        let items = [self.favoriteBarButtonItem]
        self.navigationItem.setRightBarButtonItems(items, animated: false)
    }
    
    func displayError(e: KotlinException) {
        
    }
    
    @objc fileprivate func doFavButtonClick() {
        presenter.markFavorite()
    }
    
    func displayTalk(talk: Talk, favorite: Bool) {
        self.navigationItem.title = talk.title
        self.speakersLabel.text = talk.speakerList()
        self.hoursLabel.text = talk.timeSlot()
        self.roomAndTechLabel.text = talk.roomDetail()
        self.descriptionTextView.text = talk.details()
        
        if favorite {
            self.button.tintColor = UIColor(red: 1.0, green: 0.86, blue: 0, alpha: 1.0)
        } else {
            self.button.tintColor = UIColor.gray
        }
    }
}

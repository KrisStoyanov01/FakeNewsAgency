package fakenewsagency.service;

import fakenewsagency.domain.entites.Group;
import fakenewsagency.domain.models.service.GroupServiceModel;
import fakenewsagency.error.GroupNotFoundException;
import fakenewsagency.repository.GroupRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupServiceImpl implements GroupService {
    private final ModelMapper modelMapper;
    private final GroupRepository groupRepository;

    @Autowired
    public GroupServiceImpl(ModelMapper modelMapper, GroupRepository groupRepository) {
        this.modelMapper = modelMapper;
        this.groupRepository = groupRepository;
    }

    @Override
    public GroupServiceModel addGroup(GroupServiceModel groupServiceModel) {
        Group group = this.modelMapper.map(groupServiceModel, Group.class);
        try{
            this.groupRepository.saveAndFlush(group);
            return this.modelMapper.map(group, GroupServiceModel.class);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public GroupServiceModel findGroupById(String id) {
        return this.groupRepository.findById(id)
                .map(g -> {
                    GroupServiceModel groupServiceModel = this.modelMapper.map(g, GroupServiceModel.class);
                    this.groupRepository.findById(groupServiceModel.getId());

                    return groupServiceModel;
                })
                .orElseThrow(() -> new GroupNotFoundException("Group with the given id was not found!"));
    }

    @Override
    public List<GroupServiceModel> findAllGroups() {
        return this.groupRepository.findAll()
                .stream()
                .map(g -> this.modelMapper.map(g, GroupServiceModel.class))
                .collect(Collectors.toList());
    }
}
